package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookForm {

    private Long id;                // 수정시에 id도 필요하므로 id를 변수로 선언

    private String name;
    private int price;

    private int stockQuantity;
    private String author;
    private String isbn;
}

