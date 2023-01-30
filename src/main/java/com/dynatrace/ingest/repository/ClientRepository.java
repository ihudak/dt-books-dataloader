package com.dynatrace.ingest.repository;

import com.dynatrace.ingest.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ClientRepository implements IngestRepository {
    @Value("${http.service.clients}")
    private String baseURL;
    private final RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(ClientRepository.class);
    public String getBaseURL() {
        return baseURL;
    }
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
    public ClientRepository() {
        restTemplate = new RestTemplate();
    }



    @Override
    public Client[] getAll() {
        return restTemplate.getForObject(baseURL, Client[].class);
    }

    @Override
    public void create(@Nullable Object client) {
        try {
            restTemplate.postForObject(baseURL, client == null ? Client.generate() : client, Client.class);
        } catch (Exception exception){
            logger.debug(exception.getMessage());
        }
    }

    @Override
    public void create() {
        this.create(null);
    }

    @Override
    public void update(Object object) {

    }
    @Override
    public void clearModel() {
        Client.reset();
    }
}
