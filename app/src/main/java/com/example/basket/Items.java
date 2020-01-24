package com.example.basket;

public class Items {

    private int id;
    private String listItemName;

    public Items(int id, String listName) {
        this.id = id;
        this.listItemName = listName;
    }

    public int getId() {
        return id;
    }

    public String getListName() {
        return listItemName;
    }
}
