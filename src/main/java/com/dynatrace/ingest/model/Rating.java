package com.dynatrace.ingest.model;

import com.github.javafaker.Faker;

import java.util.Random;

public class Rating implements Model {
    private long id;
    private String email;
    private String isbn;
    private int rating;
    private String comment;
    private static final Random random = new Random();
    private static final Faker faker = new Faker();

    public Rating() {
    }

    public Rating(long id, String email, String isbn, int rating, String comment) {
        this.id = id;
        this.email = email;
        this.isbn = isbn;
        this.rating = rating;
        this.comment = comment;
    }

    public static Rating generate() {
        String email = Client.getRandomEmail();
        String isbn = Book.getRandomISBN();
        if (email == null || isbn == null) {
            return null;
        }
        return new Rating(0, email, isbn, random.nextInt(4) + 1, faker.lorem().characters(true));
    }

    public static void reset() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ISBN: " + this.isbn + " Client: " + this.email;
    }
}
