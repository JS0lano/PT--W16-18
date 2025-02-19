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
public class CarStoreData {
	private Long CarStoreId;
	
	private String CarName;
	private String directions;
	private String stateOrProvince;
	private String country;
	private GeoLocation geoLocation;
	private CarStoreContributor contributor;
	private Set<String> amenities = new HashSet<>();
	
	public CarStoreData(CarStore carStore) {
		CarStoreId = carStore.getCarStoreId();
		CarName = carStore.getCarName();
		directions = carStore.getDirections();
		stateOrProvince = carStore.getStateOrProvince();
		country = carStore.getCountry();
		geoLocation = carStore.getGeoLocation();
		contributor = new CarStoreContributor(carStore.getContributor());
		
		for(Amenity amenity : carStore.getAmenties()) {
			amenities.add(amenity.getAmenity());
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class CarStoreContributor {
		private Long ContributorId;
		private String contributorName;
		private String contributorEmail;
		
		public CarStoreContributor(Contributor contributor) {
			ContributorId = contributor.getContributorId();
			contributorName = contributor.getContributorName();
			contributorEmail = getContributorEmail();		
		}
	}
}
