package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @DisplayName("상품 주문에 대한 갖가지 테스트")
    @Test
    void 상품_주문() throws Exception {
        Member member = createMember();
        Book book = createBook("시골 jpa", 10000, 10);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        System.out.println("getOrder.getStatus ========>> " + getOrder.getStatus());

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());  // 주문 상태는 ORDER 이어야 한다
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 1이어야 한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격 총합은 가격 * 주문 수량");
        assertEquals(8, book.getStockQuantity(), "상품의 재고 수량은 주문한 수량만큼 감소 해야 한다.");
    }


    @DisplayName("주문 취소에 대한 갖가지 테스트")
    @Test
    void 주문_취소() {
        Member member = createMember();                                                  // 주문 회원
        Book item = createBook("시골 jpa", 10000, 10);          // 주문 상품
        int orderCount = 2;                                                             // 주문 수량
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);    // 주문 하기

        // 주문 취소 하기 by 주문 id
        orderService.cacncelOrder(orderId);
        
        // 주문 정보 가져 오기
        Order getOrder = orderRepository.findOne(orderId);

        // 주문 취소에 대한 테스트
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 상태는 취소 이어야 한다");
        assertEquals(10, item.getStockQuantity(), "주문 취소뒤에 재고의 개수가 원복 되어야 한다");
    }

    @Test
    void 재고_수량_초과시에_예외_발생_여부_테스트() {
        // given
        Member member = createMember();
        Item item = createBook("시골 jpa", 10000, 10);
        int orderCount = 11;

        // when
        assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount)).isInstanceOf(NotEnoughStockException.class);

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("hyunsok");
        member.setAddress(new Address("남양주", "bulam", "230"));
        em.persist(member);
        return member;
    }

}
