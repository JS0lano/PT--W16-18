package car.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import car.store.controller.model.CarStoreData;
import car.store.controller.model.ContributorData;
import car.store.dao.AmenityDao;
import car.store.dao.CarStoreDao;
import car.store.dao.ContributorDao;
import car.store.entity.Amenity;
import car.store.entity.CarStore;
import car.store.entity.Contributor;

@Service
public class StoreService {

	@Autowired
	private ContributorDao contributorDao;
	
	@Autowired
	private AmenityDao amenityDao;
	
	@Autowired
	private CarStoreDao carStoreDao;

	@Transactional(readOnly = false)
	public ContributorData saveContributor(ContributorData contributorData) {
		Long contributorId = contributorData.getContributorId();
		Contributor contributor = findOrCreateContributor(contributorId, contributorData.getContributorEmail());

		setFieldsInContributor(contributor, contributorData);
		return new ContributorData(contributorDao.save(contributor));
	}

	private void setFieldsInContributor(Contributor contributor, ContributorData contributorData) {
		contributor.setContributorEmail(contributorData.getContributorEmail());
		contributor.setContributorName(contributorData.getContributorName());
	}

	private Contributor findOrCreateContributor(Long contributorId, String contributorEmail) {
		Contributor contributor;

		if (Objects.isNull(contributorId)) {
			Optional<Contributor> opContrib =
				contributorDao.findByContributorEmail(contributorEmail);
			
			if(opContrib.isPresent()) {
				throw new DuplicateKeyException(	
						"Contributor with email " + contributorEmail + "already exists.");
			}

			contributor = new Contributor();
		} else {
			contributor = findContributorById(contributorId);
		}

		return contributor;
	}

	private Contributor findContributorById(Long contributorId) {
		return contributorDao.findById(contributorId).orElseThrow(
				() -> new NoSuchElementException("Contributor with ID=" + contributorId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<ContributorData> retrieveAllContributors() {
		List<Contributor> contributors = contributorDao.findAll();
		List<ContributorData> response = new LinkedList<>();

		for (Contributor contributor : contributors) {
			response.add(new ContributorData(contributor));
		}

		return response;
	}

	@Transactional(readOnly = true)
	public ContributorData contributorById(Long contributorId) {
		Contributor contributor = findContributorById(contributorId);
		return new ContributorData(contributor);
	}
	
	@Transactional(readOnly = false)
	public void deleteContributorById(Long contributorId) {
	 Contributor contributor = findContributorById(contributorId);
	 contributorDao.delete(contributor);
	}

	@Transactional(readOnly = false)
	public CarStoreData saveCarStore(Long contributorId,
			CarStoreData carStoreData) {
		Contributor contributor = findContributorById(contributorId);
		
		Set<Amenity> amenities = amenityDao.findAllByAmenityIn(carStoreData.getAmenities());
		
		CarStore carStore = findOrCreateCarStore(carStoreData.getCarStoreId());
		setCarStoreFields(carStore, carStoreData);
		
		carStore.setContributor(contributor);
		contributor.getCarStore().add(carStore);
		
		for(Amenity amenity : amenities) {
			amenity.getCarStores().add(carStore);
			carStore.getAmenities().add(amenity);
		}
		
		CarStore dbCarStore = carStoreDao.save(carStore);
		return new CarStoreData(dbCarStore);
	}

	private void setCarStoreFields(CarStore carStore, CarStoreData carStoreData) {
		carStore.setCountry(carStoreData.getCountry());
		carStore.setDirections(carStoreData.getDirections());
		carStore.setGeoLocation(carStoreData.getGeoLocation());
		carStore.setCarName(carStoreData.getCarName());
		carStore.setCarStoreId(carStoreData.getCarStoreId());
		carStore.setStateOrProvince(carStoreData.getStateOrProvince());
	}

	private CarStore findOrCreateCarStore(Long carStoreId) {
		CarStore carStore;
		
		if(Objects.isNull(carStoreId) ) {
			carStore = new CarStore();
		}
		else {
			carStore = findCarStoreById(carStoreId);
		}
		
		return carStore;
	}

	private CarStore findCarStoreById(Long carStoreId) {
		return carStoreDao.findById(carStoreId)
			.orElseThrow(() -> new NoSuchElementException("Car store with ID=" + carStoreId));
	}

	@Transactional(readOnly = true)
	public CarStoreData retrieveCarStoreById(Long contributorId, Long storeId) {
		findContributorById(contributorId);
		CarStore carStore = findCarStoreById(storeId);
		
		if(carStore.getContributor().getContributorId() != contributorId) {
			throw new IllegalStateException("Car store with ID=" + storeId +
					" is not owned by contributor with ID=" + contributorId);
		}
		
		return new CarStoreData(carStore); 
	} 

}
