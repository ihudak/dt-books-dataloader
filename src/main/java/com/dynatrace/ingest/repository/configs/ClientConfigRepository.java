package com.dynatrace.ingest.repository.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class ClientConfigRepository implements ConfigRepository {
    @Value("${http.config.clients}")
    private String baseURL;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(ClientConfigRepository.class);
    @Override
    public String getBaseURL() { return baseURL; }
    @Override
    public String getServiceName() { return "clients"; }
    @Override
    public RestTemplate getRestTemplate() { return restTemplate; }
    @Override
    public Logger getLogger() { return logger; }
    public ClientConfigRepository() { restTemplate = new RestTemplate(); }
}
