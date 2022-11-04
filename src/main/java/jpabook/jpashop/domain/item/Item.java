package jpabook.jpashop.domain.item;
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
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 카테고리 엔티티 만든후에 활성화, 카테고리 엔티티의 items 에 의해 매핑됨
    // @ManyToMany(mappedBy = "items")
    // private List<Category> categories = new ArrayList<>();

}

