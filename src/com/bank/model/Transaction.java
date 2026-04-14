package com.bank.model;

public class Transaction {

    private int id;
    private int userId;
    private String type;
    private double amount;
    private String createdAt; // ✅ FIXED NAME

    public Transaction() {}

    public Transaction(int userId, String type, double amount) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setType(String type) { this.type = type; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}