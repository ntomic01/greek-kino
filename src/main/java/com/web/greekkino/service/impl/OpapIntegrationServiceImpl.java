package com.web.greekkino.service.impl;


import com.web.greekkino.domain.dto.OpapDrawResponse;
import com.web.greekkino.service.OpapIntegrationService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpapIntegrationServiceImpl implements OpapIntegrationService {

    private static final String SERVICE_ROOT_URI = "https://api.opap.gr";

    private final RestTemplate restTemplate;

    public OpapIntegrationServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri(SERVICE_ROOT_URI).build();
    }


    @Override
    public List<OpapDrawResponse> getUpcomingDraws() {
        // putanja koja vraca narednih 10 izvlacenja tombole
        // https://api.opap.gr/draws/v3.0/1100/upcoming/10
        // kako izvlacenja idu na svakih 5 minuta i to nam je precesto da pratimo, uzecemo samo 2 izvlacenja u satu i to na svakih pola sata, tipa u pola 2 i u 2..
        ResponseEntity<OpapDrawResponse[]> response = restTemplate.getForEntity("/draws/v3.0/1100/upcoming/10", OpapDrawResponse[].class);
        if(!response.getStatusCode().is2xxSuccessful() || response.getBody() == null){
            return new ArrayList<>();
        }
        List<OpapDrawResponse> results = new ArrayList<>();
        for(OpapDrawResponse drawResponse: response.getBody()){
            // prebacujem timeStamp u datum i vreme.
            LocalDateTime drawTime = Instant.ofEpochMilli(drawResponse.getDrawTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

            if(drawTime.getMinute() == 0 || drawTime.getMinute() == 30){
                results.add(drawResponse);
            }
        }
        return results;
    }

    @Override
    public OpapDrawResponse getCompletedDrawById(Long drawId) {
        ResponseEntity<OpapDrawResponse> response = restTemplate
                .getForEntity(String.format("/draws/v3.0/1100/%d", drawId), OpapDrawResponse.class);
        if(!response.getStatusCode().is2xxSuccessful() || response.getBody() == null){
            return null;
        }

        return response.getBody();
    }
}
