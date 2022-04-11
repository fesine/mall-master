package com.fesine.mall.lambada;

import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

/**
 * @description: 类描述
 * @author: fesine
 * @createTime:2022/4/11
 * @update:修改内容
 * @author: fesine
 * @updateTime:2022/4/11
 */
public class AuthorTest {

    @Test
    public void test() throws Throwable {
        Author author = getAuthor();
        // Optional<Author> authorOptional = Optional.ofNullable(author);
        Optional<Author> authorOptional = getAuthorOptional();
        // authorOptional.ifPresent(author1 -> System.out.println(author1.getName()));
        // Author author = authorOptional.orElseGet(Author::new);
        // Author author = authorOptional.orElseThrow(() -> new Throwable("作者为空"));
        // System.out.println(author.getName());
        // testFilter();
        authorOptional.map(Author::getBooks).ifPresent(System.out::println);

    }

    public void testFilter(){
        Optional<Author> authorOptional = getAuthorOptional();
        Optional<Author> optional = authorOptional.filter(author -> author.getAge() > 18);
        optional.ifPresent(author -> System.out.println(author.getName()));
        if (optional.isPresent()) {
            System.out.println(optional.get().getName());
        }
    }

    private Optional<Author> getAuthorOptional() {
        Book book1 = new Book("书名1", "描述1", 88);
        Book book2 = new Book("书名2", "描述2", 66);
        Author author = new Author(1L, "小李", 36, "书写新的篇章", Arrays.asList(book1, book2));
        return Optional.ofNullable(author);
    }
    private Author getAuthor() {
        Author author = new Author(1L, "小李123", 36, "书写新的篇章", null);
        return author;
    }
}
