package com.dynatrace.ingest.repository;
import com.dynatrace.ingest.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class BookRepository implements IngestRepository {
    @Value("${http.service.books}")
    private String baseURL;
    private final RestTemplate restTemplate;
    public String getBaseURL() {
        return baseURL;
    }
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
    public BookRepository() {
        restTemplate = new RestTemplate();
    }



    @Override
    public Book[] getAll() {
        return restTemplate.getForObject(baseURL, Book[].class);
    }

    @Override
    public void create(@Nullable Object book) {
        try {
            restTemplate.postForObject(baseURL, book == null ? Book.generate() : book, Book.class);
        } catch (Exception ignore){}
    }

    public void create(boolean vend, double price) {
        try {
            restTemplate.postForObject(baseURL, Book.generate(vend, price), Book.class);
        } catch (Exception ignore){}
    }

    public void create(double price) {
        try {
            restTemplate.postForObject(baseURL, Book.generate(price), Book.class);
        } catch (Exception ignore){}
    }

    public void create(boolean vend) {
        try {
            restTemplate.postForObject(baseURL, Book.generate(vend), Book.class);
        } catch (Exception ignore){}
    }

    @Override
    public void create() {
        try {
            restTemplate.postForObject(baseURL, Book.generate(), Book.class);
        } catch (Exception ignore){}
    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void clearModel() {
        Book.reset();
    }
}
