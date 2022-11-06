package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @DisplayName("상품 정보 2개를 저장한뒤 토탈 카운트를 조회 하면 2이다 !")
    @Transactional
    @Test
    public void 상품저장_테스트() {
        // 상품 데이터 생성
        Item item1 = new Item();
        item1.setName("bmw");
        item1.setPrice(10000);

        // 상품 데이터 생성
        Item item2 = new Item();
        item2.setName("bmw2");
        item2.setPrice(20000);

        itemRepository.save(item1);
        itemRepository.save(item2);


        List<Item> items = itemRepository.findAll();
        int totalCount = items.size();

        assertEquals(2, totalCount);


    }

}