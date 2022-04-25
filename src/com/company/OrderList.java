package com.company;

import java.util.ArrayList;

public class OrderList {
    private ArrayList<Order> orders = new ArrayList<>();

    public OrderList(ArrayList<Order> orders){
        this.orders = orders;
    }

    public OrderList(){

    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public String toString(){
        return String.valueOf(orders);
    }
}
