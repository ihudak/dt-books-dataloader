package com.dynatrace.ingest.model;
import com.github.javafaker.Faker;

import java.util.Objects;
import java.util.Random;

public class Book implements Model {
    private long id;
    private String isbn;
    private String title;
    private String language;
    private boolean published;
    private String author;
    private double price;

    private static Long currentISBN = 10000000000000L;
    private static final Long startISBN = 10000000000000L;
    private static final double maxRandPrice = 33.0;

    public static Long getCurrentISBN() {
        return currentISBN;
    }

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    static public Book generate() {
        return generate(faker.bool().bool());
    }
    static public Book generate(boolean vend) {
        return generate(vend, random.nextDouble(maxRandPrice));
    }

    static public Book generate(double price) {
        return generate(faker.bool().bool(), price);
    }

    static public Book generate(boolean vend, double price) {
        return new Book(0, (--currentISBN).toString(), faker.book().title(), "EN", vend, faker.book().author(), price);
    }

    static public String getRandomISBN() {
        if (Objects.equals(currentISBN, startISBN)) {
            return null;
        }
        long isbn = random.nextLong(startISBN - currentISBN) + currentISBN - 1;
        return Long.toString(isbn);
    }

    static public void reset() {
        currentISBN = startISBN;
    }

    public Book() {
    }

    public Book(long id, String isbn, String title, String language, boolean published, String author, double price) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.language = language;
        this.published = published;
        this.author = author;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
