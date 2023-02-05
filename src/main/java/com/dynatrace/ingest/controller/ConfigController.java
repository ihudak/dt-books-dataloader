package com.dynatrace.ingest.controller;

import com.dynatrace.ingest.exception.BadRequestException;
import com.dynatrace.ingest.model.Config;
import com.dynatrace.ingest.repository.configs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigController {
    @Autowired
    private ClientConfigRepository clientConfigRepository;
    @Autowired
    private BookConfigRepository bookConfigRepository;
    @Autowired
    private CartConfigRepository cartConfigRepository;
    @Autowired
    private StorageConfigRepository storageConfigRepository;
    @Autowired
    private OrderConfigRepository orderConfigRepository;
    @Autowired
    private PaymentConfigRepository paymentConfigRepository;
    @Autowired
    private DynapayConfigRepository dynapayConfigRepository;
    @Autowired
    private RatingConfigRepository ratingConfigRepository;

    private final Logger logger = LoggerFactory.getLogger(ConfigController.class);
    // get all settings
    @GetMapping("/{serviceId}")
    public List<Config> getAllConfigs(@PathVariable String serviceId) {
        ConfigRepository configRepository = getConfigRepoByServiceId(serviceId);
        if (configRepository == null) {
            BadRequestException badRequestException = new BadRequestException("Config is not supported " + serviceId);
            logger.error(badRequestException.getMessage());
            throw badRequestException;
        }
        return List.of(configRepository.getConfigsForService());
    }

    // get a setting
    @GetMapping("/{serviceId}/{id}")
    public Config getConfigById(@PathVariable String serviceId, @PathVariable String id) {
        ConfigRepository configRepository = getConfigRepoByServiceId(serviceId);
        if (configRepository == null) {
            BadRequestException badRequestException = new BadRequestException("Config is not supported " + serviceId);
            logger.error(badRequestException.getMessage());
            throw badRequestException;
        }
        return configRepository.getConfigByID(id);
    }

    // create a setting
    @PostMapping("/{serviceId}")
    public Config createConfig(@PathVariable String serviceId, @RequestBody Config config) {
        ConfigRepository configRepository = getConfigRepoByServiceId(serviceId);
        if (configRepository == null) {
            BadRequestException badRequestException = new BadRequestException("Config is not supported " + serviceId);
            logger.error(badRequestException.getMessage());
            throw badRequestException;
        }
        return configRepository.createOrUpdate(config);
    }

    // update a config
    @PutMapping("/{serviceId}/{id}")
    public Config updateConfig(@PathVariable String serviceId, @PathVariable String id, @RequestBody Config config) {
        ConfigRepository configRepository = getConfigRepoByServiceId(serviceId);
        if (configRepository == null) {
            BadRequestException badRequestException = new BadRequestException("Config is not supported " + serviceId);
            logger.error(badRequestException.getMessage());
            throw badRequestException;
        }
        if (!id.equals(config.getId())) {
            BadRequestException badRequestException = new BadRequestException("Config Id " + config.getId() + " does not match the requested " + id);
            logger.error(badRequestException.getMessage());
            throw badRequestException;
        }
        return configRepository.createOrUpdate(config);
    }

    // delete a config
    @DeleteMapping("/{serviceId}/{id}")
    public void deleteConfig(@PathVariable String serviceId, @PathVariable String id) {
        ConfigRepository configRepository = getConfigRepoByServiceId(serviceId);
        if (configRepository == null) {
            BadRequestException badRequestException = new BadRequestException("Config is not supported " + serviceId);
            logger.error(badRequestException.getMessage());
            throw badRequestException;
        }
        configRepository.delete(id);
    }

    @DeleteMapping("/delete-all")
    public void deleteAllConfigs() {

    }

    private ConfigRepository getConfigRepoByServiceId(@NonNull String serviceId) {
        return switch (serviceId) {
            case "clients", "client" -> clientConfigRepository;
            case "books", "book" -> bookConfigRepository;
            case "carts", "cart" -> cartConfigRepository;
            case "storage", "inventory", "storages", "inventories" -> storageConfigRepository;
            case "orders", "order" -> orderConfigRepository;
            case "payments", "payment" -> paymentConfigRepository;
            case "dynapay", "superpay", "dynapays", "superpays" -> dynapayConfigRepository;
            case "ratings", "rating" -> ratingConfigRepository;
            default -> null;
        };
    }
}
