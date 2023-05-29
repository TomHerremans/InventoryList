package InventoryList;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import inventorylist.models.Site;
import inventorylist.services.SiteService;
import jakarta.transaction.Transactional;

@SpringBootTest
public class SiteTest {

	@Autowired
	private SiteService siteService;
	
	@Test
	@Transactional
	public void createSiteTest() {
		Site site = new Site("Dummyhuis", "Test-created site");
		siteService.createSite(site);
		
		assertEquals(3001, siteService.getSiteById(3001).getId());
	}
	
	@Test
	public void getSiteByIdTest() {
		Site site = siteService.getSiteById(1000);
		assertEquals(1000, site.getId());
	}
	
	@Test
	public void getAllSitesTest() {
		List<Site> list = siteService.getAllSites();
		assertEquals(1000, list.get(0).getId());
		assertEquals(2000, list.get(1).getId());
		assertEquals(3000, list.get(2).getId());
	}
	
	@Test
	@Transactional
	public void updateSiteTest() {
		Site site = siteService.getSiteById(1000);
		
		site.setName("Warenhuis Aalst");
		
		siteService.updateSite(1000, site);
		
		assertEquals("Warenhuis Aalst", siteService.getSiteById(1000).getName());
	}
	
	@Test
	@Transactional
	public void deleteSiteTest() {
		siteService.deleteSite(3000);
	}
	
}
