package hu.gdf.quickaid.api.elvira;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.gdf.quickaid.model.BaseResponse;
import hu.gdf.quickaid.service.elvira.ElviraService;

@RestController
public class ElviraController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElviraController.class);

	@Autowired
	private ElviraService elviraService;

	@GetMapping(value = "/elvira", produces = "application/json")
	public ResponseEntity<BaseResponse> getTrain(@RequestParam String from, @RequestParam String to) {
		try {

			return new ResponseEntity<BaseResponse>(elviraService.getTrain(from, to), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot get train.");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
