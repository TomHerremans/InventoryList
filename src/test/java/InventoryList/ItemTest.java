package InventoryList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import inventorylist.models.Item;
import inventorylist.services.ItemService;
import inventorylist.services.SiteService;
import jakarta.transaction.Transactional;

@SpringBootTest
public class ItemTest {

	@Autowired
	private ItemService itemService;

	/// Used for testing createItem
	@Autowired
	private SiteService siteService;

	@Test
	@Transactional // Reverts changes done by test case after the test
	public void createItemTest() {
		Item item = new Item("DMMY01", "Test Dummy", 1, 100.00, "Test-created item",
				siteService.getSiteById(1000));
		itemService.createItem(item);

		assertEquals("DMMY01", itemService.getItemById(2013).getArticleCode());
	}

	@Test
	public void getItemByIdTest() {
		Item item = itemService.getItemById(1011);
		assertEquals(1011, item.getId());
	}
	
	@Test
	public void getItemsFromSiteTest() {
	List<Item> items = itemService.getItemsFromSite(siteService.getSiteById(2000));
	assertEquals(2011, items.get(0).getId());
		
	}

	@Test
	public void getAllItemsTest() {
		List<Item> list = itemService.getAllItems();
		assertEquals(1011, list.get(0).getId());
		assertEquals(1012, list.get(1).getId());
		assertEquals(1013, list.get(2).getId());
	}

	@Test
	@Transactional
	public void updateItemTest() {
		Item overwritingItem = itemService.getItemById(1011);
		overwritingItem.setArticleCode("P17080-B21-UPD");
		itemService.updateItem(1011, overwritingItem);

		assertEquals("P17080-B21-UPD", itemService.getItemById(1011).getArticleCode());
	}

	@Test
	@Transactional
	public void deleteItemTest() {
		itemService.deleteItem(1011);
		assertEquals(null, itemService.getItemById(1011));
	}

	// Other Methods
	@Test
	public void getValueOfSiteTest() {
		double total = itemService.getValueOfSite(siteService.getSiteById(1000));
		assertNotEquals(0.00, total);
	}
	
	
	@Test
	public void getValueOfAllItemsTest() {
		double total = itemService.getValueOfAllItems();
		assertNotEquals(0.00, total);
	}

	@Test
	@Transactional
	public void transferItemTest() {
		itemService.transferItem(1013, 3000, 1);
		assertEquals("P18432-B21", itemService.getItemById(2012).getArticleCode());
	}
	
}
