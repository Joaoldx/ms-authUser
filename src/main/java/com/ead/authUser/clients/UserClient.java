package com.ead.authUser.clients;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ead.authUser.dtos.CourseDto;
import com.ead.authUser.dtos.ResponsePageDto;
import com.ead.authUser.services.UtilsService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class UserClient {
    
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable) {
        
        List<CourseDto> searchResult = null;
        
        String url = utilsService.createUrl(userId, pageable);
    
        ResponseEntity<ResponsePageDto<CourseDto>> result = null;

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
       
        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.debug("Error request /courses {} ", e);
        }

        log.info("Ending request /courses userId {} ", userId);
        return result.getBody();
    }
}
