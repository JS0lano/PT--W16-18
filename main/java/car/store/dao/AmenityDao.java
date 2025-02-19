package car.store.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import car.store.entity.Amenity;

public interface AmenityDao extends JpaRepository<Amenity, Long> {

	Set<Amenity> findAllByAmenityIn(Set<String> amenities);

}
