package inventorylist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import inventorylist.models.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {

}
