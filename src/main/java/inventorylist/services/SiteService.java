package inventorylist.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventorylist.models.Site;
import inventorylist.repositories.SiteRepository;

@Service
public class SiteService {

	@Autowired
	private SiteRepository siteRepository;

	// Basic CRUD Methods
	public void createSite(Site site) {
		siteRepository.save(site);
	}

	public Site getSiteById(int id) {
		Optional<Site> site = siteRepository.findById(id);

		if (site.isPresent()) {
			return site.get();
		} else {
			System.err.println("Site not found");
			return null;
		}

	}
	
	public List<Site> getAllSites() {
		return siteRepository.findAll();
	}

	public void updateSite(int id, Site overwritingSite) {
		Site site = getSiteById(id);
		
		if(site != null) {
		site.setName(overwritingSite.getName());
		site.setComment(overwritingSite.getComment());
		
		siteRepository.save(site);
		} else {
			System.err.println("Site not found");
		}
		
	}

	public void deleteSite(int id) {
		siteRepository.delete(getSiteById(id));
	}

}
