package ru.omb.vk_api.services.httpClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.omb.vk_api.dto.ResponseUserDto;
import ru.omb.vk_api.dto.UserInfoDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class GetRequestUserInfo {

    private static final Logger log = LoggerFactory.getLogger(GetRequestUserInfo.class);
    @Value("${access_token}")
    public String access_token;
    @Value("${v}")
    public String version;

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public GetRequestUserInfo(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public ResponseUserDto sendHttpRequestGetUser(String ids, String fields)  {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String uri = "https://api.vk.ru/method/users.get?user_ids="+ ids +"&fields="+ fields+"&v=" + version + "&access_token=" + access_token + "&lang=ru";

        try {
            ResponseEntity<ResponseUserDto> responseEntity = restTemplate.getForEntity(new URI(uri), ResponseUserDto.class);
            log.info("Send Get request to user.get!");
            log.info("Status Code: {}", responseEntity.getStatusCode());

            return responseEntity.getBody();
        } catch (URISyntaxException e) {
            e.getMessage();
        }
        return null;
    }
}
