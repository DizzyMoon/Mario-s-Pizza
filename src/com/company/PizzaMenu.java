package com.company;

import java.util.ArrayList;

public class PizzaMenu {
    private ArrayList<Pizza> pizzas = new ArrayList<>();

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }
}
