package car.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import car.store.entity.Contributor;
import car.store.service.Optional;

public interface ContributorDao extends JpaRepository<Contributor, Long> {

	Optional<Contributor> findByContributorEmail(String contributorEmail);

}
