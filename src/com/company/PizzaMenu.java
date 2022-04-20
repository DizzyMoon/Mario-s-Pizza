package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class PizzaMenu {


    public void createPizzas() {
        ArrayList<String> ingredients1 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "skinke", "oregano"));
        ArrayList<String> ingredients2 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "oksefars", "oregano"));

        Pizza pizza1 = new Pizza(1, "Vesuvio", ingredients1, 57);
        Pizza pizza2 = new Pizza(2, "Amerikaner", ingredients2, 53);

        pizzas.add(pizza1);
        pizzas.add(pizza2);
    }

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

    @Override
    public String toString() {
        return "PizzaMenu{" +
            "pizzas=" + pizzas +
            '}';
    }
}
