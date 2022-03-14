package hu.gdf.quickaid.service.aidlocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.gdf.quickaid.component.JedisConnector;
import hu.gdf.quickaid.model.AidLocationDetail;
import hu.gdf.quickaid.model.AidLocationDto;
import hu.gdf.quickaid.model.BaseResponse;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class LocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

	@Value("${redis.locations}")
	private String locations;

	private ObjectMapper mapper = new ObjectMapper();

	public BaseResponse getLocations(String city) throws Exception {
		LOGGER.info("Retriving collection: '{}' for city: {}", locations, city);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		JedisPool jedisPool = JedisConnector.getPool();
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.select(1);
			baseResponse = createResponse(baseResponse, jedis, city);
			jedisPool.destroy();
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.destroy();
			throw e;
		}
	}

	private BaseResponse createResponse(BaseResponse baseResponse, Jedis jedis, String city) throws Exception {
		Map<String, String> locations = getCachedLocations(jedis, city);
		List<AidLocationDto> locationList = new ArrayList<>();
		for (String locationKey : locations.keySet()) {
			AidLocationDto locationDto = new AidLocationDto();
			locationDto.setName(locationKey);
			AidLocationDetail details = null;
			try {
				details = mapper.readValue(locations.get(locationKey), AidLocationDetail.class);
			} catch (Exception e) {
				LOGGER.error("Cannot parse location details.");
				e.printStackTrace();
			}
			locationDto.setDetails(details);
			locationList.add(locationDto);
		}
		baseResponse.setData(locationList);
		baseResponse.setHttpStatus(HttpStatus.OK);
		return baseResponse;
	}

	private Map<String, String> getCachedLocations(Jedis jedis, String city) throws Exception {
		return jedis.hgetAll(locations + "#" + city);

	}

}
