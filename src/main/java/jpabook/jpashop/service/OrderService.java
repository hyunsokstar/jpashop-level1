package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    // 주문 함수(멤버, 상품, 수량)
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 주문 상품과 주문 생성에 필요한 객체들 미리 생성
        Member member = memberRepository.findOne(memberId);         // 회원 정보
        Item item = itemRepository.findOne(itemId);                 // 상품 정보
        Delivery delivery = new Delivery();                         // 배송 정보 
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성 by (상품, 상품 가격, 수량)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 주문을 생성 하자 Order.createOrder(멤버, 배송, 주문 상품)
        Order order = Order.createOrder(member, delivery, orderItem);
        // 주문 저장
        orderRepository.save(order);
        // 주문 아이디 반환
        return order.getId();

    }

    @Transactional
    public void cacncelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);         // 주문 조회
        order.cancel();                                         // 주문 취소 (business 함수, 각종 업데이트 알아서 전파)
    }

    // 주문 조회
    public List<Order> findOrders(OrderSearch sr) {
        return orderRepository.findAllByString(sr);
    }

    // 주문 취소 하기
    @Transactional
    public void cancleOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        System.out.println("cancel order ::::::::::::::::" + order);

        // 주문 취소
        order.cancel();
    }



}
