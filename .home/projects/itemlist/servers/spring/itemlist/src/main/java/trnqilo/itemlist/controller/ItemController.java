package trnqilo.itemlist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trnqilo.itemlist.model.Item;
import trnqilo.itemlist.service.ItemService;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("items")
public class ItemController {

  private final ItemService itemService;

  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping
  public List<Item> getAllItems() {
    return itemService.getAllItems();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Item> getItemById(@PathVariable Long id) {
    return itemService.getItemById(id)
        .map(ResponseEntity::ok)
        .orElse(notFound().build());
  }

  @PostMapping
  public Item addItem(@RequestBody Item item) {
    return itemService.addItem(item);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
    return ok(itemService.updateItem(id, itemDetails));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
    itemService.deleteItem(id);
    return noContent().build();
  }

  @GetMapping("/search")
  public List<Item> searchItems(@RequestParam("keyword") String keyword) {
    return itemService.searchItems(keyword);
  }
}
