package dev.jpa.movie.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jpa.movie.movieInfo.domain.entity.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

@Component
public class ApiUtils {
    @Value("${movie.api.daily}")
    private String dailyUrl;

    @Value("${movie.api.key}")
    private String apiKey;

    //영화 데이터 반환
    public  List<Movie> getMovieData(String movieDt) throws JsonProcessingException {
        String jsonString = "";
        HashMap<String, Object> result = new HashMap<String, Object>();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        String url = addApiParameter(movieDt);

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

        ResponseEntity<?> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Object.class);

        //api 데이터 확인
        result.put("body", resultMap.getBody());

        //데이터를 제대로 전달 받았는지 확인 string형태로 파싱해줌
        ObjectMapper mapper = new ObjectMapper();
        jsonString = mapper.writeValueAsString(resultMap.getBody());

        List<Movie> movies = changeJson(jsonString);

        return movies;
    }

    //json데이터 entity로 변경
    public List<Movie> changeJson(String jsonData) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        //json 데이터 파싱
        JsonNode rootNode = objectMapper.readTree(jsonData);

        //boxOfficeResult 추출
        JsonNode tempNode = rootNode.get("boxOfficeResult");

        //dailyBoxOfficeList 추출
        JsonNode dailyTempNode = tempNode.get("dailyBoxOfficeList");

        String result = dailyTempNode.toString();

        //List 객체로 변환
        List<Movie> movies = objectMapper.readValue(result, new TypeReference<List<Movie>>() {});

        return movies;

    }

    //api 요청 url 파라미터 추가
    public String addApiParameter(String targetDt){
        StringBuilder sb = new StringBuilder();
        sb.append(dailyUrl);
        sb.append("?key=" + apiKey);
        sb.append("&targetDt=" + targetDt);

        return sb.toString();

    }
}
