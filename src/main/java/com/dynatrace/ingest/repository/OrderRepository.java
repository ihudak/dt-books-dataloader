package com.dynatrace.ingest.repository;

import com.dynatrace.ingest.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class OrderRepository implements IngestRepository {
    @Value("${http.service.orders}")
    private String baseURL;
    private final RestTemplate restTemplate;
    public String getBaseURL() {
        return baseURL;
    }
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
    public OrderRepository() {
        restTemplate = new RestTemplate();
    }

    @Override
    public Order[] getAll() {
        return restTemplate.getForObject(baseURL, Order[].class);
    }

    @Override
    public void create(@Nullable Object order) {
        try {
            restTemplate.postForObject(baseURL, order == null ? Order.generate() : order, Order.class);
        } catch (Exception ignore){}
    }

    @Override
    public void create() {
        this.create(null);
    }

    @Override
    public void update(Object object) {
        Order order = (Order) object;
        if (order == null) {
            order = Order.getRandomOrder();
        }
        if (order == null) {
            return;
        }
        String urlBuilder = baseURL + (order.isCompleted() ? "/cancel" : "/submit");
        try {
            restTemplate.postForObject(urlBuilder, order, Order.class);
        } catch (Exception ignore){}
    }
    @Override
    public void clearModel() {
        Order.reset();
    }
}