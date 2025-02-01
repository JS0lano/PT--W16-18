package car.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import car.store.entity.Amenity;
import car.store.entity.CarStore;
import car.store.entity.Contributor;
import car.store.entity.GeoLocation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContributorData {

	private Long contributorId;
	private String contributorName;
	private String contributorEmail;
	private Set<CarStoreResponse> carStore = new HashSet<>();

	public ContributorData(Contributor contributor) {
		contributorId = contributor.getContributorId();
		contributorName = contributor.getContributorName();
		contributorEmail = contributor.getContributorEmail();

		for (CarStore carStore : contributor.getCarStore()) {
			CarStore.add(new CarStoreResponse(carStore));
		}
	}

	@Data
	@NoArgsConstructor
	static class CarStoreResponse {
		private Long carStoreId;
		private String carName;
		private String directions;
		private String stateOrProvince;
		private String country;
		private GeoLocation geoLocation;
		private Set<String> amenties = new HashSet<>();

		CarStoreResponse(CarStore carStore) {
			carStoreId = carStore.getCarStoreId();
			carName = carStore.getCarName();
			directions = carStore.getDirections();
			stateOrProvince = carStore.getStateOrProvince();
			country = carStore.getCountry();
			geoLocation = new GeoLocation(carStore.getGeoLocation());

			for (Amenity amenity : carStore.getAmenties()) {
				amenities.ad(amenity.getAmenity());
			}
		}
	}
}
