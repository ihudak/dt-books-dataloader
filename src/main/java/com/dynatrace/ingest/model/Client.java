package com.dynatrace.ingest.model;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client implements Model {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private static List<Client> clients = new ArrayList<>();
    private static Faker faker = new Faker();

    public Client() {
    }

    public Client(long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static Client generate() {
        Client client = new Client(0, faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress());
        clients.add(client);
        return client;
    }

    public static String getRandomEmail() {
        if (clients.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(clients.size());
        return clients.get(index).getEmail();
    }

    public static void reset() {
        clients.clear();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
