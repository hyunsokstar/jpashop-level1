package jpabook.jpashop.domain.item;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// 카테고리 엔티티 추가후 활성화
// import jpabook.jpashop.domain.Category;0
// import org.hibernate.annotations.BatchSize;
// import java.util.ArrayList;
// import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    /* Business Function Area Start */
    // 1. 재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 2. 재고 감소
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0 ) {
            // throw new NotEnoughStockException("need more stock");
            throw new NotEnoughStockException("재고 수량이 부족 합니다 !");
        }
        this.stockQuantity = restStock;
    }
    /* Business Function Area End */
}

