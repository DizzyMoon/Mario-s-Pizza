package com.company;

public class Order {
    private Pizza pizza;
    private String note;
    private int dateOfMonth;
    private int month;
    private int hour;
    private int minute;
    private OrderStatus orderStatus;

    public Order(Pizza pizza, String note, int dateOfMonth, int month, int hour, int minute, OrderStatus orderStatus) {
        this.pizza = pizza;
        this.note = note;
        this.dateOfMonth = dateOfMonth;
        this.month = month;
        this.hour = hour;
        this.minute = minute;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public String getNote() {
        return note;
    }

    public int getDateOfMonth() {
        return dateOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public OrderStatus getStatus() {
        return orderStatus;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String toString(){
        return pizza.toString() + ", " + note;
    }
}
