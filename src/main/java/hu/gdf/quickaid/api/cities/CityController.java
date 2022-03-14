package hu.gdf.quickaid.api.cities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.gdf.quickaid.model.BaseResponse;
import hu.gdf.quickaid.service.city.CityService;

@RestController
public class CityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

	@Autowired
	CityService cityService;

	@GetMapping(value = "/cities", produces = "application/json")
	public ResponseEntity<BaseResponse> getCities() {
		try {
			return new ResponseEntity<BaseResponse>(cityService.getCities(), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot get cities.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}