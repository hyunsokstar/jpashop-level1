package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    /* Correlation Function Area Start */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /* Correlation Function Area End */


    /* Business Function Area Start */

    // 주문 데이터 생성 함수 추가
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        // 주문 객체 설정
        Order order = new Order();
        // 주문한 멤버
        order.setMember(member);
        // 주문의 배송 정보 설정
        order.setDelivery(delivery);
        // 주문 상품 정보 설정 (주문의 주문 상품 정보 설정(리스트, 가상 필드), 주문 상품의 주문 정보 설정(아이디로 참조))
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        // 주문 상태 설정
        order.setStatus(OrderStatus.ORDER);
        // 주문 날짜 설정
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // 주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료인 상품은 취소 할수 없습니다");
        }
        // 주문 상태 취소로 만들기
        this.setStatus(OrderStatus.CANCEL);
        // 주문한 상품들에 대해 취소 하기 (주문 상품 각각의 재고 수량을 다시 원복)
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 전체 주문 가격 조회
    public int getTotalPrice() {
        int totalPrice = 0 ;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    /* Business Function Area End */


}
