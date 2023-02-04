package com.dynatrace.ingest.model;

import java.util.Random;

public class Cart implements Model {
    private long id;
    private String email;
    private String isbn;
    private int quantity;
    private static final Random random = new Random();

    public Cart() {
    }

    public Cart(long id, String email, String isbn, int quantity) {
        this.id = id;
        this.email = email;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public static Cart generate() {
        String email = Client.getRandomEmail();
        String isbn = Book.getRandomISBN();
        if (email == null || isbn == null) {
            return null;
        }
        return new Cart(0, email, isbn, random.nextInt(3) + 1);
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book " + this.isbn + " client " + this.email;
    }
}
