package hu.gdf.quickaid.api.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.gdf.quickaid.model.BaseResponse;
import hu.gdf.quickaid.service.stock.StockService;

@RestController
public class StockController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

	@Autowired
	StockService stockService;

	@GetMapping(value = "/stocks", produces = "application/json")
	public ResponseEntity<BaseResponse> getStocks(@RequestParam String city, @RequestParam String location) {
		try {
			return new ResponseEntity<BaseResponse>(stockService.getStocks(city, location), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot get stocks.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}