package car.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import car.store.entity.CarStore;

public interface CarStoreDao extends JpaRepository<CarStore, Long> {

}
