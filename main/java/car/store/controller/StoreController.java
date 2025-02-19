package car.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import car.store.controller.model.CarStoreData;
import car.store.controller.model.ContributorData;
import car.store.service.StoreService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/car_store")
@Slf4j
public class StoreController {
	@Autowired
	private StoreService storeService;

	@PostMapping("/contributor")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ContributorData insertContributor(@RequestBody ContributorData contributorData) {
		log.info("Creating contributor {}", contributorData);
		return storeService.saveContributor(contributorData);
	}

	@PutMapping("/contributor/{contributorId}")
	public ContributorData updateContributor(@PathVariable Long contributorId,
			@RequestBody ContributorData contributorData) {
		contributorData.setContributorId(contributorId);
		log.info("Updating contributor {}", contributorData);
		return storeService.saveContributor(contributorData);
	}

	@GetMapping("/contributor")
	public List<ContributorData> retrieveAllContributors() {
		log.info("Retrieve all contributors called.");
		return storeService.retrieveAllContributors();
	}

	@GetMapping("/contributor//{contributorId}")
	public ContributorData retrieveContributorById(@PathVariable Long contributorId) {
		log.info("Retrieving contributor with ID={}", contributorId);
		return storeService.contributorById(contributorId);
	}

	@DeleteMapping("/contributor/{contributorId}")
	public Map<String, String> deleteContributorById(@PathVariable Long contributorId) {
		log.info("Deleting contributor with ID ={}", contributorId);
		storeService.deleteContributorById(contributorId);

		return Map.of("message", "Contributor with ID =" + contributorId + " deleted succesfully.");
	}

	@DeleteMapping("/contributor")
	public void deleteAllContributors() {
		log.info("Attempting to delete all contributors");
		throw new UnsupportedOperationException("Delete all contributors is not allowed.");
	}

	@PutMapping("/contributor/ {contributorId}/store/{storeId}")
	public CarStoreData updateCarStore(@PathVariable Long contributorId,
			@PathVariable Long storeId,
			@RequestBody CarStoreData carStoreData) {

		carStoreData.setCarStoreId(storeId);
		
		log.info("Creating store {} for contributor with ID={}", carStoreData,
				contributorId);
		
		return storeService.saveCarStore(contributorId, carStoreData);
	}
	
	@GetMapping("/contributor/{contributorId}/store/{storeId}")
	public CarStoreData retrieveCarStoreById(@PathVariable Long contributorId,
			@PathVariable Long storeId)  {
		log.info("Retrieving car store with ID ={} for contributor with ID={}",
				storeId, contributorId);
		
		return storeService.retrieveCarStoreById(contributorId, storeId);
		
	}
}
