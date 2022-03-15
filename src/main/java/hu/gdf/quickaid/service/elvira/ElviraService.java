package hu.gdf.quickaid.service.elvira;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.gdf.quickaid.model.BaseResponse;
import hu.gdf.quickaid.model.elvira.ElviraResponse;
import hu.gdf.quickaid.model.elvira.Timetable;
import hu.gdf.quickaid.model.elvira.TrainResponse;

@Service
public class ElviraService {

	private ObjectMapper mapper = new ObjectMapper();

	private static final Logger LOGGER = LoggerFactory.getLogger(ElviraService.class);

	public BaseResponse getTrain(String from, String to) {

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

		if (from == null || to == null || from.equals(to)) {
			baseResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
			return baseResponse;
		}

		if (from.trim().equals("") || to.trim().equals("")) {
			baseResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
			return baseResponse;
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu.MM.dd HH:mm:ss");
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime budapestTime = now.withZoneSameInstant(ZoneId.of("Europe/Budapest"));

		LocalTime localBud = LocalTime.of(budapestTime.getHour(), budapestTime.getMinute());

		String url = "https://apiv2.oroszi.net/elvira?from=" + from + "&to=" + to + "&date="
				+ budapestTime.format(dtf).toString().split(" ")[0];
		LOGGER.info(url);

		RestTemplate restTemplate = new RestTemplate();

		List<TrainResponse> trainResponses = new ArrayList<>();
		int counter = 0;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			ResponseEntity<ElviraResponse> elviraResponse = restTemplate.exchange(url, HttpMethod.GET, entity,
					ElviraResponse.class);

			for (Timetable timetable : elviraResponse.getBody().getTimetable()) {
				if (counter == 3) {
					break;
				}
				LocalTime trainTime = LocalTime.of(Integer.valueOf(timetable.getStarttime().substring(0, 2)),
						Integer.valueOf(timetable.getStarttime().substring(3, 5)));

				if (trainTime.isAfter(localBud)) {
					TrainResponse trainResponse = new TrainResponse();
					trainResponse.setStartTime(timetable.getStarttime());
					trainResponse.setStart(timetable.getStart());
					trainResponse.setDestinationTime(timetable.getDestinationtime());
					trainResponse.setDestination(timetable.getDestination());
					trainResponse.setType(timetable.getType());
					trainResponse.setClassName(timetable.getClass_name());
					trainResponses.add(trainResponse);
					counter++;
				}

			}

			baseResponse.setData(trainResponses);
			baseResponse.setHttpStatus(HttpStatus.OK);

			return baseResponse;
		} catch (HttpServerErrorException hr) {
			LOGGER.error("Wrong elvira call");
			return baseResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return baseResponse;
		}
	}

}
