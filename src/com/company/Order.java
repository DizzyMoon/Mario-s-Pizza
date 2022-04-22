package com.company;

public class Order {
    private Pizza pizza;
    private String note;
    private int dateOfMonth;
    private int month;
    private int hour;
    private int minute;
    private OrderStatus orderStatus;
    private boolean hasPickupTime;
    private int takeawayHour;
    private int takeawayMinute;

    public Order(Pizza pizza, String note, int dateOfMonth, int month, int hour, int minute, OrderStatus orderStatus) {
        this.pizza = pizza;
        this.note = note;
        this.dateOfMonth = dateOfMonth;
        this.month = month;
        this.hour = hour;
        this.minute = minute;
        this.orderStatus = orderStatus;
        hasPickupTime = false;
    }

    public Order(Pizza pizza, String note, int dateOfMonth, int month, int hour, int minute, OrderStatus orderStatus, int takeawayHour, int takeawayMinute) {
        this.pizza = pizza;
        this.note = note;
        this.dateOfMonth = dateOfMonth;
        this.month = month;
        this.hour = hour;
        this.minute = minute;
        this.orderStatus = orderStatus;
        this.takeawayHour = takeawayHour;
        this.takeawayMinute = takeawayMinute;
        hasPickupTime = true;
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

    public boolean getHasPickupTime() {
        return hasPickupTime;
    }

    public int getTakeawayHour() {
        return takeawayHour;
    }

    public int getTakeawayMinute() {
        return takeawayMinute;
    }

    public String toString(){
        return pizza.toString() + ", " + note;
    }
}
