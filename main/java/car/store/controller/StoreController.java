package car.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
	public ContributorData insertContributor(
		@RequestBody ContributorData contributoData) {
	log.info("Creating contributor {}", contributoData);
	return storeService.saveContributor(contributoData);
	}
	
	@GetMapping("/contributor")
	public List<ContributorData> retrieveAllContributors() {
		log.info("Retrieve all contributors called.");
		return storeService.retrieveAllContributors();
	}
	
	
	@GetMapping("/contributor//{contributorId}")
	public ContributorData retrieveContributorById(@PathVariable Long contributorId) {
		log.info("Retrieving contributor with ID={}",contributorId);
		return storeService.contributorById(contributorId);
	}
}
