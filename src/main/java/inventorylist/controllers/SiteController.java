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

import inventorylist.models.Site;
import inventorylist.services.SiteService;

@RestController
public class SiteController {

	@Autowired
	private SiteService siteService;

	// Basic CRUD Methods
	@PostMapping("/sites")
	public ResponseEntity<Object> createSite(
			@RequestParam String name,
			@RequestParam(required = false) String comment) {
		siteService.createSite(new Site(name, comment));
		return ResponseEntity.status(201).body(null);
	}
	
	@GetMapping("/sites")
	public ResponseEntity<Site> getSiteById(@RequestParam int id) {
		Site site = siteService.getSiteById(id);
		
		if (site != null) {
			return ResponseEntity.status(302).body(site);
		} else {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	@GetMapping("/sites/all")
	public ResponseEntity<List<Site>> getAllSites() {
		return ResponseEntity.status(200).body(siteService.getAllSites());
	}
	
	@PutMapping("/sites")
	public void updateSite(
			@RequestParam int id,
			@RequestParam String name,
			@RequestParam(required = false) String comment
			) {
		
		siteService.updateSite(id, new Site(name, comment));
		
	}
	
	@DeleteMapping("/sites")
	public void deleteSite(@RequestParam int id) {
		siteService.deleteSite(id);
	}
	
	
	
}
