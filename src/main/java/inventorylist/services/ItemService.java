package inventorylist.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import inventorylist.models.Item;
import inventorylist.models.Site;
import inventorylist.repositories.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired // Used for transferItem
	private SiteService siteService;

	// Basic CRUD Methods
	public void createItem(Item item) {
		itemRepository.save(item);
	}

	public Item getItemById(int id) {
		Optional<Item> item = itemRepository.findById(id);

		if (item.isPresent()) {
			return item.get();
		} else {
			return null;
		}
	}

	public List<Item> getItemsFromSite(Site site) {
		List<Item> list = new ArrayList<Item>();

		for (Item item : getAllItems()) {
			if (item.getSite().getId() == site.getId()) {
				list.add(item);
			}
		}
		return list;
	}

	public List<Item> getAllItems() {
		return itemRepository.findAll();

	}

	public void updateItem(int id, Item overwritingItem) {
		Item item = getItemById(id);

		if (item != null) {
			item.setArticleCode(overwritingItem.getArticleCode());
			item.setName(overwritingItem.getName());
			item.setQuantity(overwritingItem.getQuantity());
			item.setValue(overwritingItem.getValue());
			item.setComment(overwritingItem.getComment());

			itemRepository.save(item);
			// Issue: Method does not call entity's PostUpdate annotation
		} else {
			System.err.println("Item not found");
		}

	}

	public void deleteItem(int id) {
		Item item = getItemById(id);

		if (item != null) {
			itemRepository.delete(item);
		} else {
			System.err.println("Item cannot be found or has already been deleted");
		}
	}

	// Other Methods
	public double getValueOfSite(Site site) {
		double sum = 0;
		List<Item> items = getItemsFromSite(site);

		for (Item item : items) {
			sum += (item.getValue() * item.getQuantity());
		}

		return sum;

	}

	public double getValueOfAllItems() {
		double sum = 0;
		List<Item> items = getAllItems();

		for (Item item : items) {
			sum += (item.getValue() * item.getQuantity());
		}

		return sum;
	}

	public void transferItem(int itemId, int targetSiteId, int amount) {

		Item itemToTransfer = getItemById(itemId);
		Site site = itemToTransfer.getSite();
		Site targetSite = siteService.getSiteById(targetSiteId);

		// Commences transfer of item if all checks are passed
		if (
			Objects.nonNull(site) &
			Objects.nonNull(targetSite) &
			notSameSite(site, targetSite) &
			enoughItems(itemToTransfer, amount)
				) {

			// Lowers quantity of item by amount
			itemToTransfer.setQuantity(itemToTransfer.getQuantity() - amount);
			updateItem(itemId, itemToTransfer);
			
			// Gets item in another site with the same article code
			Item itemInSiteWithSameCode = getItemInSiteWithSameCode(targetSite, itemToTransfer.getArticleCode());

			// Checks if that item exists
			if (Objects.nonNull(itemInSiteWithSameCode)) {
				// Updates item if item in other site DOES exist
				System.out.println("it does exist lmao");
				itemInSiteWithSameCode.setQuantity(itemInSiteWithSameCode.getQuantity()+amount);
				updateItem(itemInSiteWithSameCode.getId(), itemInSiteWithSameCode);
			} else {
				// Creates item if item in other site DOES NOT exist
				System.out.println("it does not exist lmao");
				Item newItem = new Item(itemToTransfer.getArticleCode(), itemToTransfer.getName(), amount,
						itemToTransfer.getValue(), itemToTransfer.getComment(), targetSite);
				createItem(newItem);
			}

		}

	}
	
	// Method for transferItem
	// Checks if the site being transferred to is not the same
	private boolean notSameSite (Site site, Site targetSite) {
		if (site.getId() != targetSite.getId()) {
			return true;
		} else {
			System.err.println("An item cannot be transferred to the same site");
			return false;
		}
	}
	
	// Method for transferItem
	// Checks if there is enough of that item to transfer
	private boolean enoughItems (Item item, int amount) {
		if (amount > item.getQuantity()) {
			System.err.println("Not enough of that item to transfer!");
			return false;
		} else {
			return true;
		}	
	}
	
	// Method for transferItem
	// Checks if another item in target site with the same article code exists
	private Item getItemInSiteWithSameCode(Site targetSite, String targetArticleCode) {
		List<Item> itemsInTargetSite = getItemsFromSite(targetSite);
		for(Item item : itemsInTargetSite) {
			if (item.getArticleCode().equals(targetArticleCode)) {
				return item;
			}
		}
		return null;
	}

}
