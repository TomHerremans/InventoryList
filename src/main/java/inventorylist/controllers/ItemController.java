package inventorylist.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import inventorylist.models.Item;
import inventorylist.services.ItemService;
import inventorylist.services.SiteService;

@RestController
public class ItemController {

	@Autowired
	ItemService itemService;
	
	@Autowired
	SiteService siteService;
	
	// Basic CRUD Methods
	@PostMapping("/items")
	public ResponseEntity<Void> createItem(
			@RequestParam int siteId,
			@RequestParam String articleCode,
			@RequestParam String name,
			@RequestParam int quantity,
			@RequestParam double value,
			@RequestParam(required = false) String comment) {
		itemService.createItem(new Item(articleCode, name, quantity, value, comment, siteService.getSiteById(siteId)));
		return ResponseEntity.status(201).body(null);
	}
	
	@GetMapping("/items")
	public ResponseEntity<Item> getItemById(@RequestParam int id) {
		Item item = itemService.getItemById(id);
		
		if (item != null) {
			return ResponseEntity.status(302).body(item);
		} else {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	@GetMapping("/items/all")
	public ResponseEntity<List<Item>> getAllItems() {
		return ResponseEntity.status(200).body(itemService.getAllItems());
	}
	
	@PutMapping("/items")
	public void updateItem(
			@RequestParam int id,
			@RequestParam String articleCode,
			@RequestParam String name,
			@RequestParam int quantity,
			@RequestParam double value,
			@RequestParam(required = false) String comment) { 
		
		itemService.updateItem(id, new Item(articleCode, name, quantity, value, comment, null));
	}
	
	@DeleteMapping("/items")
	public void deleteItem(@RequestParam int id) {
		itemService.deleteItem(id);
	}
	
	// Other Methods
	@GetMapping("/items/value")
	public double getValueOfSite(@RequestParam int id) {
		return itemService.getValueOfSite(siteService.getSiteById(id));
	}
	
	@GetMapping("/items/value/all")
	public double getValueOfAllItems() {
		return itemService.getValueOfAllItems();
	}
	
	@PostMapping("/items/transfer")
	public void transferItem(
			@RequestParam int itemId,
			@RequestParam int targetSiteId,
			@RequestParam int amount) {
		itemService.transferItem(itemId, targetSiteId, amount);
	}
	
}
