package hu.gdf.quickaid.api.stock;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@DeleteMapping(value = "/stocks", produces = "application/json")
	public ResponseEntity<BaseResponse> deleteStock(HttpServletRequest request, @RequestParam String city,
			@RequestParam String location, @RequestParam String stock) {
		try {
			return new ResponseEntity<BaseResponse>(stockService.deleteStock(request, city, location, stock),
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot delete stocks.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping(value = "/stocks", produces = "application/json")
	public ResponseEntity<BaseResponse> modifyStock(HttpServletRequest request, @RequestParam String city,
			@RequestParam String location, @RequestParam String stock) {
		try {
			return new ResponseEntity<BaseResponse>(stockService.createStock(request, city, location, stock),
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot create stocks.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping(value = "/stocks/add", produces = "application/json")
	public ResponseEntity<BaseResponse> addStock(HttpServletRequest request, @RequestParam String city,
			@RequestParam String location, @RequestParam String stock) {
		try {
			return new ResponseEntity<BaseResponse>(stockService.addStock(request, city, location, stock),
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot add stock.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PatchMapping(value = "/stocks/remove", produces = "application/json")
	public ResponseEntity<BaseResponse> removeStock(HttpServletRequest request, @RequestParam String city,
			@RequestParam String location, @RequestParam String stock) {
		try {
			return new ResponseEntity<BaseResponse>(stockService.removeStock(request, city, location, stock),
					HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Cannot remove stock.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}