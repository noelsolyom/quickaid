package hu.gdf.quickaid.api.aidlocations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.gdf.quickaid.model.BaseResponse;
import hu.gdf.quickaid.service.aidlocation.LocationService;

@RestController
public class LocationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

	@Autowired
	LocationService locationService;

	@GetMapping(value = "/locations", produces = "application/json")
	public ResponseEntity<BaseResponse> getLocations(@RequestParam String city) {
		try {
			return new ResponseEntity<BaseResponse>(locationService.getLocations(city), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot get locations.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
