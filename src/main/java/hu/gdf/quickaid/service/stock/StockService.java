package hu.gdf.quickaid.service.stock;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.gdf.quickaid.component.JedisConnector;
import hu.gdf.quickaid.model.AidLocationDto;
import hu.gdf.quickaid.model.BaseResponse;
import hu.gdf.quickaid.model.StockDto;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class StockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

	@Value("${redis.locations}")
	private String locations;

	@Value("${redis.stocks}")
	private String stocks;

	@Value("${master.admin.apikey}")
	private String masterAdminApiKey;

	private ObjectMapper mapper = new ObjectMapper();

	public BaseResponse getStocks(String city, String location) throws Exception {
		LOGGER.info("Retriving collection: '{}' for location: {}", stocks, location);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			baseResponse = createResponse(baseResponse, jedis, city, location);
			jedisPool.destroy();
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.destroy();
			throw e;
		}
	}

	public BaseResponse deleteStock(HttpServletRequest request, String city, String location, String stock)
			throws Exception {
		LOGGER.info("Trying to delete stock: '{}' in city: '{}' in location: {}.", stock, city, location);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		String apiKey = request.getHeader("api-key");

		if (apiKey != null) {
			JedisPool jedisPool = JedisConnector.getPool();
			try (Jedis jedis = jedisPool.getResource()) {
				if (isLocationKey(apiKey, jedis, city, location) || masterAdminApiKey.equals(apiKey)) {
					baseResponse = deleteStockAuth(baseResponse, jedis, city, location, stock);
					jedisPool.destroy();
					return baseResponse;
				} else {
					baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
					jedisPool.destroy();
					return baseResponse;
				}
			}
		} else {
			baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			return baseResponse;
		}

	}

	private BaseResponse deleteStockAuth(BaseResponse baseResponse, Jedis jedis, String city, String location,
			String stock) throws Exception {
		LOGGER.info("Deleting stock: {}.", stock);
		jedis.select(1);
		jedis.hdel(stocks + "#" + city + "#" + location, stock);
		baseResponse = createResponse(baseResponse, jedis, city, location);
		return baseResponse;
	}

	public BaseResponse createStock(HttpServletRequest request, String city, String location, String stock)
			throws Exception {
		LOGGER.info("Trying to create stock: '{}' in city: '{}' in location: {}.", stock, city, location);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		if (stock == null || stock.trim().length() == 0) {
			baseResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
			return baseResponse;
		}

		String apiKey = request.getHeader("api-key");

		if (apiKey != null) {
			JedisPool jedisPool = JedisConnector.getPool();
			try (Jedis jedis = jedisPool.getResource()) {
				if (isLocationKey(apiKey, jedis, city, location) || masterAdminApiKey.equals(apiKey)) {
					baseResponse = createStockAuth(baseResponse, jedis, city, location, stock);
					jedisPool.destroy();
					return baseResponse;
				} else {
					baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
					jedisPool.destroy();
					return baseResponse;
				}
			}
		} else {
			baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			return baseResponse;
		}
	}

	private BaseResponse createStockAuth(BaseResponse baseResponse, Jedis jedis, String city, String location,
			String stock) throws Exception {
		LOGGER.info("Deleting stock: {}.", stock);
		jedis.select(1);
		Map<String, String> map = new HashMap<>();
		map.put(stock, "10");
		jedis.hset(stocks + "#" + city + "#" + location, map);
		baseResponse = createResponse(baseResponse, jedis, city, location);
		return baseResponse;
	}

	public BaseResponse addStock(HttpServletRequest request, String city, String location, String stock)
			throws Exception {
		LOGGER.info("Trying to add stock: '{}' in city: '{}' in location: {}.", stock, city, location);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		String apiKey = request.getHeader("api-key");

		if (apiKey != null) {
			JedisPool jedisPool = JedisConnector.getPool();
			try (Jedis jedis = jedisPool.getResource()) {
				if (isLocationKey(apiKey, jedis, city, location) || masterAdminApiKey.equals(apiKey)) {
					baseResponse = addStockAuth(baseResponse, jedis, city, location, stock);
					jedisPool.destroy();
					return baseResponse;
				} else {
					baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
					jedisPool.destroy();
					return baseResponse;
				}
			}
		} else {
			baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			return baseResponse;
		}
	}

	private BaseResponse addStockAuth(BaseResponse baseResponse, Jedis jedis, String city, String location,
			String stock) throws Exception {
		LOGGER.info("Adding stock: {}.", stock);
		jedis.select(1);

		Map<String, String> map = getCachedStocks(jedis, city, location);
		Integer currentStock = Integer.valueOf(map.get(stock));
		if (currentStock < 20) {
			currentStock++;
		}
		Map<String, String> newStock = new HashMap<>();
		newStock.put(stock, currentStock.toString());
		jedis.hset(stocks + "#" + city + "#" + location, newStock);

		baseResponse = createResponse(baseResponse, jedis, city, location);
		return baseResponse;
	}

	public BaseResponse removeStock(HttpServletRequest request, String city, String location, String stock)
			throws Exception {
		LOGGER.info("Trying to remove stock: '{}' in city: '{}' in location: {}.", stock, city, location);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		String apiKey = request.getHeader("api-key");

		if (apiKey != null) {
			JedisPool jedisPool = JedisConnector.getPool();
			try (Jedis jedis = jedisPool.getResource()) {
				if (isLocationKey(apiKey, jedis, city, location) || masterAdminApiKey.equals(apiKey)) {
					baseResponse = removeStockAuth(baseResponse, jedis, city, location, stock);
					jedisPool.destroy();
					return baseResponse;
				} else {
					baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
					jedisPool.destroy();
					return baseResponse;
				}
			}
		} else {
			baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			return baseResponse;
		}
	}

	private BaseResponse removeStockAuth(BaseResponse baseResponse, Jedis jedis, String city, String location,
			String stock) throws Exception {
		LOGGER.info("Removing stock: {}.", stock);
		jedis.select(1);

		Map<String, String> map = getCachedStocks(jedis, city, location);
		Integer currentStock = Integer.valueOf(map.get(stock));
		if (currentStock > 0) {
			currentStock--;
		}
		Map<String, String> newStock = new HashMap<>();
		newStock.put(stock, currentStock.toString());
		jedis.hset(stocks + "#" + city + "#" + location, newStock);

		baseResponse = createResponse(baseResponse, jedis, city, location);
		return baseResponse;
	}

	private BaseResponse createResponse(BaseResponse baseResponse, Jedis jedis, String city, String location)
			throws Exception {
		jedis.select(1);
		Map<String, String> stocks = getCachedStocks(jedis, city, location);
		List<StockDto> stockList = new ArrayList<>();
		for (String stockKey : stocks.keySet()) {
			StockDto stock = new StockDto();
			stock.setName(stockKey);
			stock.setValue(Integer.valueOf(stocks.get(stockKey)));
			stockList.add(stock);
		}

		class StockDtoComparator implements Comparator<StockDto> {
			Collator spCollator = Collator.getInstance(new Locale("hu", "HU"));

			@Override
			public int compare(StockDto e1, StockDto e2) {
				return spCollator.compare(e1.getName(), e2.getName());
			}
		}

		Collections.sort(stockList, new StockDtoComparator());

		baseResponse.setData(stockList);
		baseResponse.setHttpStatus(HttpStatus.OK);
		return baseResponse;
	}

	private boolean isLocationKey(String key, Jedis jedis, String city, String location) throws Exception {
		jedis.select(0);
		Map<String, String> locations = getCachedLocations(jedis, city);
		if (key.equals(mapper.readValue(locations.get(location), AidLocationDto.class).getDetails().getKey())) {
			return true;
		} else {
			return false;
		}
	}

	private Map<String, String> getCachedLocations(Jedis jedis, String city) throws Exception {
		return jedis.hgetAll(locations + "#" + city);
	}

	private Map<String, String> getCachedStocks(Jedis jedis, String city, String location) throws Exception {
		return jedis.hgetAll(stocks + "#" + city + "#" + location);
	}

}