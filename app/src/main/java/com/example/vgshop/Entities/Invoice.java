package com.example.vgshop.Entities;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class Invoice implements Serializable {

    @DocumentId
    private String id;
    private String name;
    private String description;
    private int amount;
    private double price;
    private double total;
    private String category;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotal(double total) {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
