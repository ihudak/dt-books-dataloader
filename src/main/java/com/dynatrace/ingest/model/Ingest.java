package com.dynatrace.ingest.model;

public class Ingest {
    private int code;
    private String message;
    private int numBooksVend;
    private int numBooksNotvend;
    private int numBooksRandVend;
    private int numClients;
    private int numCarts;
    private int numOrders;
    private int numSubmitOrders;
    private int numRatings;
    private int numStorage;
    private int numBooksPerStorage;
    private int numBooksPerOrder;
    private boolean randomPrice;


    public Ingest() {
    }

    public Ingest(int code, String message, int numBooksVend, int numBooksNotvend, int numBooksRandVend, int numClients, int numCarts, int numOrders, int numSubmitOrders, int numRatings, int numStorage, int numBooksPerStorage, int numBooksPerOrder, boolean randomPrice) {
        this.code = code;
        this.message = message;
        this.numBooksVend = numBooksVend;
        this.numBooksNotvend = numBooksNotvend;
        this.numBooksRandVend = numBooksRandVend;
        this.numClients = numClients;
        this.numCarts = numCarts;
        this.numOrders = numOrders;
        this.numSubmitOrders = numSubmitOrders;
        this.numRatings = numRatings;
        this.numStorage = numStorage;
        this.numBooksPerStorage = numBooksPerStorage;
        this.numBooksPerOrder = numBooksPerOrder;
        this.randomPrice = randomPrice;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumBooksVend() {
        return numBooksVend;
    }

    public void setNumBooksVend(int numBooksVend) {
        this.numBooksVend = numBooksVend;
    }

    public int getNumBooksNotvend() {
        return numBooksNotvend;
    }

    public void setNumBooksNotvend(int numBooksNotvend) {
        this.numBooksNotvend = numBooksNotvend;
    }

    public int getNumBooksRandVend() {
        return numBooksRandVend;
    }

    public void setNumBooksRandVend(int numBooksRandVend) {
        this.numBooksRandVend = numBooksRandVend;
    }

    public int getNumClients() {
        return numClients;
    }

    public void setNumClients(int numClients) {
        this.numClients = numClients;
    }

    public int getNumCarts() {
        return numCarts;
    }

    public void setNumCarts(int numCarts) {
        this.numCarts = numCarts;
    }

    public int getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
    }

    public int getNumSubmitOrders() {
        return numSubmitOrders;
    }

    public void setNumSubmitOrders(int numSubmitOrders) {
        this.numSubmitOrders = numSubmitOrders;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public int getNumStorage() {
        return numStorage;
    }

    public void setNumStorage(int numStorage) {
        this.numStorage = numStorage;
    }

    public int getNumBooksPerStorage() {
        return numBooksPerStorage;
    }

    public void setNumBooksPerStorage(int numBooksPerStorage) {
        this.numBooksPerStorage = numBooksPerStorage;
    }

    public int getNumBooksPerOrder() {
        return numBooksPerOrder;
    }

    public void setNumBooksPerOrder(int numBooksPerOrder) {
        this.numBooksPerOrder = numBooksPerOrder;
    }

    public boolean isRandomPrice() {
        return randomPrice;
    }

    public void setRandomPrice(boolean randomPrice) {
        this.randomPrice = randomPrice;
    }

    @Override
    public String toString() {
        return "Books " + this.numBooksNotvend + this.numBooksVend + this.numBooksRandVend + " Clients " + this.numClients;
    }
}
