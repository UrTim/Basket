package com.example.basket;

public class Basket {

    private int id;
    private String itemName;

    public Basket(int id, String itemName) {
        this.id = id;
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }
}
