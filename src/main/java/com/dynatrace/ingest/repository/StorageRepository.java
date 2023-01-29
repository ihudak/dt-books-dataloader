package com.dynatrace.ingest.repository;

import com.dynatrace.ingest.exception.BadRequestException;
import com.dynatrace.ingest.model.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class StorageRepository implements IngestRepository {
    @Value("${http.service.storage}")
    private String baseURL;
    private final RestTemplate restTemplate;
    public String getBaseURL() {
        return baseURL;
    }
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
    public StorageRepository() {
        restTemplate = new RestTemplate();
    }



    public Storage buyBook(@NonNull Storage storage) {
        String urlBuilder = baseURL +
                "/sell-book";
        Storage storageNew = restTemplate.postForObject(urlBuilder, storage, Storage.class);
        if (storageNew == null || storageNew.getQuantity() < 0) {
            throw new BadRequestException("Purchase was rejected, ISBN: " + storage.getIsbn());
        }
        return storageNew;
    }

    public Storage returnBook(@NonNull Storage storage) {
        String urlBuilder = baseURL +
                "/ingest-book";

        Storage storageNew = restTemplate.postForObject(urlBuilder, storage, Storage.class);
        if (storageNew == null || storageNew.getQuantity() < 0) {
            throw new BadRequestException("Return was rejected, ISBN: " + storage.getIsbn());
        }
        return storageNew;
    }

    @Override
    public Storage[] getAll() {
        return restTemplate.getForObject(baseURL, Storage[].class);
    }

    @Override
    public void create(@Nullable Object bookInStorage) {
        try {
            restTemplate.postForObject(baseURL, bookInStorage == null ? Storage.generate() : bookInStorage, Storage.class);
        } catch (Exception ignore){}
    }

    @Override
    public void create() {
        this.create(null);
    }

    @Override
    public void update(Object object) {
        if (null != object) {
            this.buyBook((Storage) object);
        }
    }
    @Override
    public void clearModel() {
        Storage.reset();
    }
}
