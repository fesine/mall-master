package com.fesine.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.fesine.mall.dao.ProductMapper;
import com.fesine.mall.form.CartAddForm;
import com.fesine.mall.form.CartUpdateForm;
import com.fesine.mall.pojo.Cart;
import com.fesine.mall.pojo.Product;
import com.fesine.mall.service.ICartService;
import com.fesine.mall.vo.CartProductVo;
import com.fesine.mall.vo.CartVo;
import com.fesine.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fesine.mall.enums.ProductEnum.ON_SALE;
import static com.fesine.mall.enums.ResponseEnum.*;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2021/5/20
 * @update:修改内容
 * @author: fesine
 * @updateTime:2021/5/20
 */
@Service
@Slf4j
public class CartServiceImpl implements ICartService {

    private static final String CART_REDIS_KEY_TEMPLATE="cart_%d";
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 添加购物车
     *
     * @param cartAddForm
     * @return
     */
    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm cartAddForm) {
        Integer quantity = 1;
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        //商品是否存在
        if (product == null) {
            return ResponseVo.error(PRODUCT_NOT_EXIST);
        }

        //商品是否正常
        if(!product.getStatus().equals(ON_SALE.getCode())){
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }

        //库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(PRODUCT_STOCK_ERROR);
        }
        //写入redis
        //redisTemplate.opsForValue().set(String.format(CART_REDIS_KEY_TEMPLATE, uid),
        //        JSON.toJSONString(new Cart(product.getId(), quantity,cartAddForm.getSelected())));
        //cart_1,i,value
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String pid = String.valueOf(product.getId());
        String value = opsForHash.get(redisKey, pid);
        Cart cart;
        if (StringUtils.isEmpty(value)) {
            //没有该商品，则添加
            cart = new Cart(product.getId(), quantity,cartAddForm.getSelected());
        }else{
            //累计
            cart = JSON.parseObject(value, Cart.class);
            cart.setQuantity(cart.getQuantity()+quantity);

        }
        opsForHash.put(redisKey, pid,JSON.toJSONString(cart));

        return list(uid);
    }

    /**
     * 查询购物车
     *
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> value = opsForHash.entries(redisKey);
        CartVo cartVo = new CartVo();
        boolean selectedAll = true;
        Integer totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        for (Map.Entry<String, String> entry : value.entrySet()) {
            //商品id
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = JSON.parseObject(entry.getValue(), Cart.class);
            //TODO 需要优化，不要在循环里面操作数据库，使用in条件查询
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVo productVo = new CartProductVo(
                        productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected());
                cartProductVoList.add(productVo);
                if (!cart.getProductSelected()) {
                    selectedAll = false;
                }
                if (cart.getProductSelected()) {
                    totalPrice = totalPrice.add(productVo.getProductTotalPrice());
                }
                totalQuantity +=cart.getQuantity();
            }
        }
        //只要有一个没选中就不是全选
        cartVo.setSelectedAll(selectedAll);
        cartVo.setCartTotalQuantity(totalQuantity);
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    /**
     * 更新购物车
     *
     * @param uid
     * @param productId
     * @param cartUpdateForm
     * @return
     */
    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isEmpty(value)) {
            //没有该商品，报错
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
        }
        Cart cart = JSON.parseObject(value, Cart.class);
        if (cart.getQuantity() != null && cart.getQuantity() >= 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if (cartUpdateForm.getSelected() != null) {
            cart.setProductSelected(cartUpdateForm.getSelected());
        }
        opsForHash.put(redisKey, String.valueOf(productId), JSON.toJSONString(cart));
        return list(uid);
    }

    /**
     * 删除购物车
     *
     * @param uid
     * @param productId
     * @return
     */
    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isEmpty(value)) {
            //没有该商品，报错
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
        }
        opsForHash.delete(redisKey, String.valueOf(productId));
        return list(uid);
    }

    /**
     * 全选
     *
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey,String.valueOf(cart.getProductId()),JSON.toJSONString(cart));
        }
        return list(uid);
    }

    /**
     * 全不选
     *
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), JSON.toJSONString(cart));
        }
        return list(uid);
    }

    /**
     * 获取购物车总数
     *
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    private List<Cart> listForCart(Integer uid){
        List<Cart> list = new ArrayList<>();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        HashOperations<String, String, String> opsForHash =
                redisTemplate.opsForHash();
        Map<String, String> value = opsForHash.entries(redisKey);
        for (String s : value.values()) {
            list.add(JSON.parseObject(s, Cart.class));
        }
        return list;
    }
}
