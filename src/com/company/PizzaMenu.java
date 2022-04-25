package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class PizzaMenu {

    private ArrayList<Pizza> pizzas = new ArrayList<>();

    @Override
    public String toString() {
        return "PizzaMenu{" +
                "pizzas=" + pizzas +
                '}';
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    public void createPizzas() {
        ArrayList<String> ingredients1 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "skinke", "oregano"));
        ArrayList<String> ingredients2 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "oksefars", "oregano"));
        ArrayList<String> ingredients3 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "pepperoni", "oregano"));
        ArrayList<String> ingredients4 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "kødsauce", "spaghetti","cocktailpøser","oregano"));
        ArrayList<String> ingredients5 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "skinke", "pepperoni", "cocktailpølser","oregano"));
        ArrayList<String> ingredients6 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "bacon","oregano"));
        ArrayList<String> ingredients7 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "pepperoni", "rød peber", "løg", "oliven", "oregano"));
        ArrayList<String> ingredients8 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "skinke", "ananas", "champignon", "løg", "oregano"));
        ArrayList<String> ingredients9 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "skinke", "bacon", "kebab", "chili", "oregano"));
        ArrayList<String> ingredients10 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost","skinke","champignon", "oregano"));
        ArrayList<String> ingredients11 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "skinke", "ananas", "oregano"));
        ArrayList<String> ingredients12 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "skinke", "rejer", "oregano"));
        ArrayList<String> ingredients13 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "skinke", "bacon", "oregano"));
        ArrayList<String> ingredients14 = new ArrayList<String>(Arrays.asList("tomatsauce", "ost", "pepperoni", "bacon", "løg", "oregano"));

        Pizza pizza1 = new Pizza(1, "Vesuvio", ingredients1, 57);
        Pizza pizza2 = new Pizza(2, "Amerikaner", ingredients2, 53);
        Pizza pizza3 = new Pizza(3, "Cacciatore", ingredients3, 57);
        Pizza pizza4 = new Pizza(4, "Carbona", ingredients4, 63);
        Pizza pizza5 = new Pizza(5, "Dennis", ingredients5, 65);
        Pizza pizza6 = new Pizza(6, "Bertil", ingredients6, 57);
        Pizza pizza7 = new Pizza(7, "Silvia", ingredients7, 61);
        Pizza pizza8 = new Pizza(8, "Victoria", ingredients8, 61);
        Pizza pizza9 = new Pizza(9, "Toronfo", ingredients9, 61);
        Pizza pizza10 = new Pizza(10, "Capricciosa", ingredients10, 61);
        Pizza pizza11 = new Pizza(11, "Hawaii", ingredients11, 61);
        Pizza pizza12 = new Pizza(12, "Le Blissola", ingredients12, 61);
        Pizza pizza13 = new Pizza(13, "Venezia", ingredients13, 61);
        Pizza pizza14 = new Pizza(14, "Mafia", ingredients14, 61);

        pizzas.add(pizza1);
        pizzas.add(pizza2);
        pizzas.add(pizza3);
        pizzas.add(pizza4);
        pizzas.add(pizza5);
        pizzas.add(pizza6);
        pizzas.add(pizza7);
        pizzas.add(pizza8);
        pizzas.add(pizza9);
        pizzas.add(pizza10);
        pizzas.add(pizza11);
        pizzas.add(pizza12);
        pizzas.add(pizza13);
        pizzas.add(pizza14);
    }
}
