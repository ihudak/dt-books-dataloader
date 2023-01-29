package com.dynatrace.ingest.repository;

import com.dynatrace.ingest.model.Rating;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class RatingRepository implements IngestRepository {
    @Value("${http.service.ratings}")
    private String baseURL;
    private final RestTemplate restTemplate;
    public String getBaseURL() {
        return baseURL;
    }
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
    public RatingRepository() {
        restTemplate = new RestTemplate();
    }


    @Override
    public Rating[] getAll() {
        return restTemplate.getForObject(baseURL, Rating[].class);
    }

    @Override
    public void create(@Nullable Object rating) {
        try {
            restTemplate.postForObject(baseURL, rating == null ? Rating.generate() : rating, Rating.class);
        } catch (Exception ignore){}
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
        Rating.reset();
    }
}
