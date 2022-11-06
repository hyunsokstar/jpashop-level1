package jpabook.jpashop.service;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @DisplayName("상품 2개를 단순 저장 한뒤 총 아이템 개수가 2개임을 확인")
    @Transactional
    @Test
    public void 단순_상품_저장(){
        Item item1 = new Item();
        item1.setName("kanu coffe");

        Item item2 = new Item();
        item2.setName("top coffe");

        itemService.saveItem(item1);
        itemService.saveItem(item2);

        List<Item> items = itemService.findItems();
        int total_count = items.size();

        assertEquals(2, total_count);

    }

    @DisplayName("저장한 상품들의 리스트와 전체 상품 조회 결과 리스트는 같아야 한다.")
    @Transactional
    @Test
    public void 전체_상품_목록_조회_테스트() {
        // ipad 저장
        Item item1 = new Item();
        item1.setName("ipad");
        item1.setPrice(20000);

        // galaxy 저장
        Item item2 = new Item();
        item2.setName("galaxy");
        item2.setPrice(18000);

        // 상품 데이터 2개 저장
        itemService.saveItem(item1);
        itemService.saveItem(item2);

        // 예상 리스트
        List<Item> expectedResult = Arrays.asList(item1, item2);

        // 실제 리스트
        List<Item> itemList = itemService.findItems();

        assertThat(itemList).isEqualTo(expectedResult);

    }

    @DisplayName("아이디로 조회한 상품의 아이디는 저장한 상품의 아이디와 같아야 한다.")
    @Transactional
    @Test
    public void 아이디로_하나의_상품_조회(){
        // 상품 데이터 생성
        Item item1 = new Item();
        item1.setName("bmw");
        item1.setPrice(10000);

        // 상품 데이터 생성
        Item item2 = new Item();
        item2.setName("bmw2");
        item2.setPrice(20000);

        // 상품 데이터 2개 저장
        itemService.saveItem(item1);
        itemService.saveItem(item2);

        // 첫번째 item id 조회
        Long savedFirstItemId = item1.getId();
        // 첫번재 item id로 상품 엔티티 조회
        Long resultItemId = itemService.findOne(savedFirstItemId).getId();

        // 예상 실제 비교
        assertEquals(savedFirstItemId, resultItemId);
    }

}