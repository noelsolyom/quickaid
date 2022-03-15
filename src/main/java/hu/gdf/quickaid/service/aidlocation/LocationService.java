package hu.gdf.quickaid.service.aidlocation;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class LocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

	@Value("${redis.locations}")
	private String locations;

	@Value("${master.admin.apikey}")
	private String masterAdminApiKey;

	private ObjectMapper mapper = new ObjectMapper();

	public BaseResponse getLocations(String city) throws Exception {
		LOGGER.info("Retriving collection: '{}' for city: {}", locations, city);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.select(0);
			baseResponse = createResponse(baseResponse, jedis, city);
			jedisPool.destroy();
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.destroy();
			throw e;
		}
	}

	public BaseResponse deleteLocation(HttpServletRequest request, String city, String location) throws Exception {
		LOGGER.info("Trying to delete location: '{}' in city: '{}'.", location, city);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		if (masterAdminApiKey.equals(request.getHeader("api-key"))) {
			return deleteLocationAuth(baseResponse, city, location);
		} else {
			baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			return baseResponse;
		}
	}

	private BaseResponse deleteLocationAuth(BaseResponse baseResponse, String city, String location) {
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.select(0);
			LOGGER.info("Deleting location: {}.", location);
			jedis.hdel(locations + "#" + city, location);
			baseResponse = createResponse(baseResponse, jedis, city);
			jedisPool.destroy();
			return baseResponse;
		} catch (Exception e) {
			LOGGER.error("Cannot delete location '{}'.", location);
			e.printStackTrace();
			jedisPool.destroy();
			return baseResponse;
		}
	}

	public BaseResponse setNewLocation(HttpServletRequest request, String city, String location,
			AidLocationDto locationDto) throws Exception {
		LOGGER.info("Trying to set new location: '{}' in city: '{}'.", location, city);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		if (masterAdminApiKey.equals(request.getHeader("api-key"))) {
			return setNewLocationAuth(baseResponse, city, location, locationDto);
		} else {
			baseResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
			return baseResponse;
		}
	}

	private BaseResponse setNewLocationAuth(BaseResponse baseResponse, String city, String location,
			AidLocationDto locationDto) {
		LOGGER.info("Setting new location: {}", locationDto);
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.select(0);
			LOGGER.info("Setting new location: {}.", location);
			String newLocationString = mapper.writeValueAsString(locationDto);
			jedis.hset(locations + "#" + city, location, newLocationString);
			baseResponse = createResponse(baseResponse, jedis, city);
			jedisPool.destroy();
			return baseResponse;
		} catch (Exception e) {
			LOGGER.error("Cannot delete location '{}'.", location);
			e.printStackTrace();
			jedisPool.destroy();
			return baseResponse;
		}
	}

	private BaseResponse createResponse(BaseResponse baseResponse, Jedis jedis, String city) throws Exception {
		Map<String, String> locations = getCachedLocations(jedis, city);
		List<AidLocationDto> locationList = new ArrayList<>();
		for (String locationKey : locations.keySet()) {
			AidLocationDto locationDto = new AidLocationDto();
			try {
				locationDto = mapper.readValue(locations.get(locationKey), AidLocationDto.class);
				locationDto.getDetails().setKey(null);
				locationDto.setName(locationKey);
			} catch (Exception e) {
				LOGGER.error("Cannot parse location details.");
				e.printStackTrace();
			}
			locationList.add(locationDto);
		}

		class AidLocationDtoComparator implements Comparator<AidLocationDto> {
			Collator spCollator = Collator.getInstance(new Locale("hu", "HU"));

			@Override
			public int compare(AidLocationDto e1, AidLocationDto e2) {
				return spCollator.compare(e1.getName(), e2.getName());
			}
		}

		Collections.sort(locationList, new AidLocationDtoComparator());

		baseResponse.setData(locationList);
		baseResponse.setHttpStatus(HttpStatus.OK);
		return baseResponse;
	}

	private Map<String, String> getCachedLocations(Jedis jedis, String city) throws Exception {
		return jedis.hgetAll(locations + "#" + city);
	}

}
