package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @DisplayName("테스트 내용 or 주제")
    @Transactional
    @Test
    public void 주문생성테스트() {

        Member member1 = new Member();
        member1.setName("hyun");

        Address address = new Address("byulnae", "bulam", "220");
        member1.setAddress(address);

        //  Order order = new Order();
        //  order.setMember();

    }

}