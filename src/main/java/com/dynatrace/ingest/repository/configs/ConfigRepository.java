package com.dynatrace.ingest.repository.configs;

import com.dynatrace.ingest.exception.BadRequestException;
import com.dynatrace.ingest.model.Config;
import org.slf4j.Logger;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

public interface ConfigRepository {
    String getBaseURL();
    String getServiceName();
    RestTemplate getRestTemplate();
    Logger getLogger();

    default Config getConfigByID(String id) {
        String urlBuilder = getBaseURL() + "/" + id;
        getLogger().info("Getting config " + id + " for service. URL: " + urlBuilder);
        try {
            Config config = getRestTemplate().getForObject(urlBuilder, Config.class);
            config.setServiceId(getServiceName());
            return config;
        } catch (Exception exception) {
            getLogger().error(exception.getMessage());
            throw new BadRequestException(exception.getMessage());
        }
    }

    default Config[] getConfigsForService() {
        getLogger().info("Getting all configs for service. URL: " + getBaseURL());
        try {
            return getRestTemplate().getForObject(getBaseURL(), Config[].class);
        } catch (Exception exception) {
            getLogger().error(exception.getMessage());
            throw new BadRequestException(exception.getMessage());
        }
    }

    default Config createOrUpdate(@NonNull Config config) {
        String urlBuilder = getBaseURL();
        getLogger().info("Creating/Updating config " + config.getId() + " for service. URL: " + urlBuilder);
        try {
            return getRestTemplate().postForObject(urlBuilder, config, Config.class);
        } catch (Exception exception) {
            getLogger().error(exception.getMessage());
            throw new BadRequestException(exception.getMessage());
        }
    }

    default void delete(String id) {
        String urlBuilder = getBaseURL() + "/" + id;
        getLogger().info("Deleting config for service. URL: " + urlBuilder);
        try {
            getRestTemplate().delete(urlBuilder);
        } catch (Exception exception) {
            getLogger().error(exception.getMessage());
            throw new BadRequestException(exception.getMessage());
        }
    }
}
