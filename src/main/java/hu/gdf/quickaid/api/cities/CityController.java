package hu.gdf.quickaid.api.cities;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@PutMapping(value = "/cities", produces = "application/json")
	public ResponseEntity<BaseResponse> setNewCity(HttpServletRequest request, @RequestParam String city) {
		try {
			return new ResponseEntity<BaseResponse>(cityService.setNewCity(request, city), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot set city.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@DeleteMapping(value = "/cities", produces = "application/json")
	public ResponseEntity<BaseResponse> deleteCity(HttpServletRequest request, @RequestParam String city) {
		try {
			return new ResponseEntity<BaseResponse>(cityService.deleteCity(request, city), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot delete city.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

}