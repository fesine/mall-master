package com.fesine.mall.service.impl;

import com.fesine.mall.dao.OrderItemMapper;
import com.fesine.mall.dao.OrderMapper;
import com.fesine.mall.dao.ProductMapper;
import com.fesine.mall.dao.ShippingMapper;
import com.fesine.mall.enums.OrderStatusEnum;
import com.fesine.mall.enums.PaymentTypeEnum;
import com.fesine.mall.enums.ProductEnum;
import com.fesine.mall.enums.ResponseEnum;
import com.fesine.mall.pojo.*;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.service.IOrderService;
import com.fesine.mall.vo.OrderItemVo;
import com.fesine.mall.vo.OrderVo;
import com.fesine.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/28
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/28
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 创建订单
     *
     * @param uid
     * @param shippingId
     * @return
     */
    @Transactional
    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //校验收货地址
        Shipping shipping = shippingMapper.selectByIdAndUserId(uid, shippingId);
        if (shipping == null) {
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        //获取购物车、校验(是否有商品，库存)
        List<Cart> cartList =
                cartService.listForCart(uid)
                        .stream().filter(Cart::getProductSelected)
                        .collect(Collectors.toList());

        //计算总价格(被选中部分)
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_SELECTED_IS_EMPTY);
        }
        //转成set
        Set<Integer> productIdSet =
                cartList.stream().map(Cart::getProductId).collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        //转成map
        Map<Integer, Product> productMap =
                productList.stream()
                        .collect(Collectors.toMap(Product::getId, product -> product));

        List<OrderItem> itemList = new ArrayList<>();
        Long orderNo = generatorOrderNo();
        // 生成订单，入库：order和order_item，事务控制
        for (Cart cart : cartList) {
            Product product = productMap.get(cart.getProductId());
            if (product == null) {
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "商品不存在:" + cart.getProductId());
            }
            if(!ProductEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,
                        "商品已下架或已删除:" + product.getName());
            }
            //判断库存是否充足
            if (product.getStock() <= cart.getQuantity()) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,
                        "库存不足:" + product.getName());
            }
            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            itemList.add(orderItem);
            //减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int i = productMapper.updateByPrimaryKeySelective(product);
            if (i <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }
        //生成订单
        Order order = buildOrder(uid, orderNo, shippingId, itemList);
        int i = orderMapper.insertSelective(order);
        if (i <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        //插入订单详情表
         i = orderItemMapper.batchInsert(itemList);
        if (i <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        //更新购物车(删除选中的商品)，操作redis
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }

        // 构造orderVo
        OrderVo orderVo = buildOrderVo(order, itemList, shipping);
        return ResponseVo.success(orderVo);
    }

    /**
     * 查询订单列表
     *
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(uid);
        PageInfo pageInfo = new PageInfo<>(orderList);
        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo).collect(Collectors.toSet());
        List<OrderItem> itemList =
                orderItemMapper.selectByOrderNoSet(orderNoSet);
        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId).collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
        //生成以orderNo为key，value为对应orderNo list的map对象
        Map<Long, List<OrderItem>> itemMap =
                itemList.stream().collect(Collectors.groupingBy(OrderItem::getOrderNo));
        Map<Integer, Shipping> shippingMap =
                shippingList.stream().collect(Collectors.toMap(Shipping::getId,
                        shipping -> shipping));
        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order, itemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }
        pageInfo.setList(orderVoList);
        return ResponseVo.success(pageInfo);
    }

    /**
     * 查看订单详情
     *
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null || !uid.equals(order.getUserId())){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(orderNo);
        List<OrderItem> itemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        OrderVo orderVo = buildOrderVo(order, itemList, shipping);
        return ResponseVo.success(orderVo);
    }

    /**
     * 取消订单
     *
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !uid.equals(order.getUserId())) {
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //只有未付款的订单才可以取消
        if(!OrderStatusEnum.NO_PAY.getCode().equals(order.getStatus())){
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }
        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int i = orderMapper.updateByPrimaryKeySelective(order);
        if (i <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> itemList, Shipping shipping) {
        OrderVo orderVo= new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        List<OrderItemVo> itemVoList = itemList.stream().map(e -> {
            OrderItemVo itemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, itemVo);
            return itemVo;
        }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(itemVoList);
        if (shipping != null) {
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }
        return orderVo;
    }

    private Order buildOrder(Integer uid, Long orderNo, Integer shippingId,
                             List<OrderItem> itemList) {
        Order order = new Order();
        order.setUserId(uid);
        order.setOrderNo(orderNo);
        order.setShippingId(shippingId);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        //总金额
        BigDecimal payment =
                itemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO,
                        BigDecimal::add);
        order.setPayment(payment);
        return order;
    }

    private Long generatorOrderNo() {
        return System.currentTimeMillis()+new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNo,Integer quantity, Product product) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        item.setQuantity(quantity);
        return item;
    }
}
