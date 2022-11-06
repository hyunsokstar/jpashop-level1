package jpabook.jpashop.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class
OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량


    /* Business Function Area Start */

    // 주문 상품 생성
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        // 주문 상품 객체 생성
        OrderItem orderItem = new OrderItem();

        // 상품 설정
        orderItem.setItem(item);
        // 가격 설정
        orderItem.setOrderPrice(orderPrice);
        // 수량 설정
        orderItem.setCount(count);
        // 재고 수량 감소
        item.removeStock(count);

        // 주문 상품 객체 반환
        return orderItem;
    }

    
    // 주문 취소시 주문 상품 수량을 재고 수량에 다시 추가
    public void cancel() {
        getItem().addStock(count);
    }

    // 주문 상품 가격 계산
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    /* Business Function Area End */

}
