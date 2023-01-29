package com.dynatrace.ingest.repository;

import com.dynatrace.ingest.model.Cart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class CartRepository implements IngestRepository {
    @Value("${http.service.cart}")
    private String baseURL;
    private final RestTemplate restTemplate;
    public String getBaseURL() {
        return baseURL;
    }
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
    public CartRepository() {
        restTemplate = new RestTemplate();
    }

    @Override
    public Cart[] getAll() {
        return restTemplate.getForObject(baseURL, Cart[].class);
    }

    @Override
    public void create(@Nullable Object cart) {
        try {
            restTemplate.postForObject(baseURL, cart == null ? Cart.generate() : cart, Cart.class);
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
        Cart.reset();
    }
}
