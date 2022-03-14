package hu.gdf.quickaid.service.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import hu.gdf.quickaid.component.JedisConnector;
import hu.gdf.quickaid.model.BaseResponse;
import hu.gdf.quickaid.model.StockDto;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class StockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

	@Value("${redis.stocks}")
	private String stocks;

	public BaseResponse getStocks(String city, String location) throws Exception {
		LOGGER.info("Retriving collection: '{}' for location: {}", stocks, location);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.select(1);
			baseResponse = createResponse(baseResponse, jedis, city, location);
			jedisPool.destroy();
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.destroy();
			throw e;
		}
	}

	private BaseResponse createResponse(BaseResponse baseResponse, Jedis jedis, String city, String location)
			throws Exception {
		Map<String, String> stocks = getCachedStocks(jedis, city, location);
		List<StockDto> stockList = new ArrayList<>();
		for (String stockKey : stocks.keySet()) {
			StockDto stock = new StockDto();
			stock.setName(stockKey);
			stock.setValue(Integer.valueOf(stocks.get(stockKey)));
			stockList.add(stock);
		}
		baseResponse.setData(stockList);
		baseResponse.setHttpStatus(HttpStatus.OK);
		return baseResponse;
	}

	private Map<String, String> getCachedStocks(Jedis jedis, String city, String location) throws Exception {
		return jedis.hgetAll(stocks + "#" + city + "#" + location);
	}
}