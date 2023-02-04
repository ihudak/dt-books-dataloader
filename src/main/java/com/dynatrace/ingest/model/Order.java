package com.dynatrace.ingest.model;

import com.dynatrace.ingest.controller.IngestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order implements Model {
    private long id;
    private String email;
    private String isbn;
    private int quantity;
    private double price;
    private boolean completed;
    private static final Random random = new Random();
    private static List<Order> orders = new ArrayList<>();
    static Logger logger = LoggerFactory.getLogger(Order.class);

    public Order() {
    }

    public Order(long id, String email, String isbn, int quantity, double price, boolean completed) {
        this.id = id;
        this.email = email;
        this.isbn = isbn;
        this.quantity = quantity;
        this.price = price;
        this.completed = completed;
    }

    public static Order generate() {
        String email = Client.getRandomEmail();
        String isbn = Book.getRandomISBN();
        logger.info("GENERATING ORDER");
        logger.info(isbn);
        logger.info(email);
        if (email == null || isbn == null) {
            logger.info("GENERATING ORDER FAILED");
            return null;
        }
        Order order = new Order(0, email, isbn, random.nextInt(3) + 1, 12.0, false);
        orders.add(order);
        logger.info(order.toString());
        return order;
    }

    public static Order getRandomOrder() {
        if (orders.isEmpty()) {
            return null;
        }
        int index = random.nextInt(orders.size());
        return orders.get(index);
    }

    public static void reset() {
        orders.clear();
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ISBN: " + this.isbn + " Client: " + this.email;
    }
}
