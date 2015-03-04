package trnqilo.itemlist.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trnqilo.itemlist.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
  @Query("SELECT i FROM Item i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
      "OR LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
  List<Item> search(@Param("keyword") String keyword);
}
