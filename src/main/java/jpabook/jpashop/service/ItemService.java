package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /** 상품 1개 저장 */
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /** 상품 전체 조회 */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // 상품 1개 조회 by id
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
