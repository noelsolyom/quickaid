package hu.gdf.quickaid.service.city;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

	@Value("${master.admin.apikey}")
	private String masterAdminApiKey;

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

	public BaseResponse setNewCity(HttpServletRequest request, String city) throws Exception {
		LOGGER.info("Trying to set new city: '{}'", city);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		if (masterAdminApiKey.equals(request.getHeader("api-key"))) {
			return setNewCityAuth(baseResponse, city);
		} else {
			baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			return baseResponse;
		}
	}

	private BaseResponse setNewCityAuth(BaseResponse baseResponse, String city) {
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.select(0);
			LOGGER.info("Setting new city: {}.", city);
			jedis.sadd(cities, city);
			baseResponse = createResponse(baseResponse, jedis);
			jedisPool.destroy();
			return baseResponse;
		} catch (Exception e) {
			LOGGER.error("Cannot set new city '{}'.", city);
			e.printStackTrace();
			jedisPool.destroy();
			return baseResponse;
		}
	}

	public BaseResponse deleteCity(HttpServletRequest request, String city) throws Exception {
		LOGGER.info("Trying to delete city: '{}'", city);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		if (masterAdminApiKey.equals(request.getHeader("api-key"))) {
			return deleteCityAuth(baseResponse, city);
		} else {
			baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			return baseResponse;
		}
	}

	private BaseResponse deleteCityAuth(BaseResponse baseResponse, String city) {
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.select(0);
			LOGGER.info("Deleting city: {}.", city);
			jedis.srem(cities, city);
			baseResponse = createResponse(baseResponse, jedis);
			jedisPool.destroy();
			return baseResponse;
		} catch (Exception e) {
			LOGGER.error("Cannot delete city '{}'.", city);
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

		class CityDtoComparator implements Comparator<CityDto> {
			Collator spCollator = Collator.getInstance(new Locale("hu", "HU"));

			@Override
			public int compare(CityDto e1, CityDto e2) {
				return spCollator.compare(e1.getCity(), e2.getCity());
			}
		}

		Collections.sort(cityList, new CityDtoComparator());

		baseResponse.setData(cityList);
		baseResponse.setHttpStatus(HttpStatus.OK);
		return baseResponse;
	}

	private Set<String> getCachedCities(Jedis jedis) {
		return jedis.smembers(cities);
	}

}