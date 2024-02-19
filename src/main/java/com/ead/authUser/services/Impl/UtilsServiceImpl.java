package com.ead.authUser.services.Impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ead.authUser.services.UtilsService;

@Service
public class UtilsServiceImpl implements UtilsService {

    String REQUEST_URI = "http://localhost:8082/courses";
    
    public String createUrl(UUID userId, Pageable pageable) {
        String url = REQUEST_URI + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size=" 
        + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(":", ",");

        return url;
    }
}
