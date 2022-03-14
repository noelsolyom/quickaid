package hu.gdf.quickaid.service.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import hu.gdf.quickaid.component.JedisConnector;
import hu.gdf.quickaid.model.BaseResponse;
import hu.gdf.quickaid.model.CityDto;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class CityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityService.class);

	@Value("${redis.cities}")
	private String cities;

	public BaseResponse getCities() throws Exception {
		LOGGER.info("Retriving collection: '{}'", cities);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.select(0);
			baseResponse = createResponse(baseResponse, jedis);
			jedisPool.destroy();
			return baseResponse;

		} catch (Exception e) {
			LOGGER.error("Cannot retrive collection '{}'.", cities);
			e.printStackTrace();
			jedisPool.destroy();
			return baseResponse;
		}
	}

	private BaseResponse createResponse(BaseResponse baseResponse, Jedis jedis) {
		Set<String> cities = getCachedCities(jedis);
		List<CityDto> cityList = new ArrayList<>();
		for (String city : cities) {
			cityList.add(new CityDto(city));
		}
		baseResponse.setData(cityList);
		baseResponse.setHttpStatus(HttpStatus.OK);
		return baseResponse;
	}

	private Set<String> getCachedCities(Jedis jedis) {
		return jedis.smembers(cities);
	}

}