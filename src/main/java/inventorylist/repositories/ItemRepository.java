package inventorylist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inventorylist.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	Item findByArticleCode(String articleCode);
	
}
