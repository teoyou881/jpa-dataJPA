package spring.data_jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.data_jpa.entity.Item;

@SpringBootTest
class ItemRepositoryTest {


  @Autowired
  ItemRepository itemRepository;

  @Test
  public void save() {
    Item item = new Item("A");
    itemRepository.save(item);
  }
}