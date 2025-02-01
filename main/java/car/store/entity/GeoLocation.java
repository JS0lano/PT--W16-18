package car.store.entity;

import java.math.BigDecimal;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class GeoLocation {
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	public GeoLocation(GeoLocation geolocation) {
		this.latitude = geolocation.latitude;
		this.longitude = geolocation.longitude;
	}
}
