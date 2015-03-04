package trnqilo.itemlist.service;

import org.springframework.stereotype.Service;
import trnqilo.itemlist.model.Item;
import trnqilo.itemlist.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

  private final ItemRepository itemRepository;

  public ItemService(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  public Optional<Item> getItemById(Long id) {
    return itemRepository.findById(id);
  }

  public Item addItem(Item item) {
    return itemRepository.save(item);
  }

  public Item updateItem(Long id, Item itemDetails) {
    return itemRepository.findById(id).map(item -> {
      item.setName(itemDetails.getName());
      item.setDescription(itemDetails.getDescription());
      item.setQuantity(itemDetails.getQuantity());
      return itemRepository.save(item);
    }).orElseThrow(() -> new RuntimeException("Item not found with id " + id));
  }

  public void deleteItem(Long id) {
    itemRepository.deleteById(id);
  }

  public List<Item> searchItems(String keyword) {
    return itemRepository.search(keyword);
  }
}
