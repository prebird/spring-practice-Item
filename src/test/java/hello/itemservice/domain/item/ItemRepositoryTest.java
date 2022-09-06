package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void AfterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("A", 10000, 10);

        //when
        Item result = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(result);
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("A", 10000, 10);
        Item item2 = new Item("B", 10000, 20);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);

    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("A", 100000, 2);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        Item updateParam = new Item("B", 200000, 3);
        itemRepository.updateItem(itemId, updateParam);

        //then
        Item findNewItem = itemRepository.findById(itemId);
        assertThat(findNewItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findNewItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findNewItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

    @Test
    void clearStore() {
    }
}