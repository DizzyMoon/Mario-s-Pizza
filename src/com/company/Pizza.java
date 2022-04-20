package com.company;

import java.util.ArrayList;

public class Pizza {
    private final int number;
    private final String name;
    private final ArrayList<String> ingredients;
    private final int price;

    public Pizza(int number, String name, ArrayList<String> ingredients, int price) {
        this.number = number;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }
}
