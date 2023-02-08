package com.dynatrace.ingest.repository.versions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class RatingsVersionRepository implements VersionRepository {
    @Value("${http.version.ratings}")
    private String baseURL;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(RatingsVersionRepository.class);
    @Override
    public String getBaseURL() { return baseURL; }
    @Override
    public String getServiceName() { return "ratings"; }
    @Override
    public RestTemplate getRestTemplate() { return restTemplate; }
    @Override
    public Logger getLogger() { return logger; }
    public RatingsVersionRepository() { restTemplate = new RestTemplate(); }
}
