package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Controller {

  private OrderList orderList = new OrderList();
  private OrderHistory orderHistory = new OrderHistory();
  private PizzaMenu pizzaMenu = new PizzaMenu();

  private Scanner sc = new Scanner(System.in);
  private boolean running = true;

  public void run(){

    while (running) {
      int input = sc.nextInt();

      switch (input) {
        case 1 -> showMenu();
        case 2 -> showOrder();
        case 3 -> showOrderHistory();
        case 4 -> addOrder();
        case 5 -> setOrderStatus();
        case 6 -> exit();
      }
    }

  }

  public void showMenu() {
    pizzaMenu.createPizzas();
    System.out.println(pizzaMenu.getPizzas());

  }

  public void showOrder() {

  }


  public void showOrderHistory() {
    for (Order order : orderList.getOrders()){
      System.out.println(order);
    }
  }

  public void addOrder() {
    Pizza pizza1 = new Pizza(1, "Hawaii", new ArrayList<String>(Arrays.asList("Ananas")), 64);
    Order order1 = new Order(pizza1, "Uden brød", 15, 3, 5, 4, OrderStatus.ORDERED);
    orderList.addOrder(order1);
  }

  public void setOrderStatus() {
    String spacing = createSpacing("Hawaii pizza");
    System.out.println("Hawaii Pizza" + spacing);
  }

  public void exit(){
    running = false;
  }

  public String createSpacing(String initiate) {
    StringBuilder sb = new StringBuilder();
    for (int i = 50 - initiate.length(); i != 0; i--) {
      sb.append(".");
    }
    sb.append(":");
    String spacing = String.valueOf(sb);
    return spacing;
  }

}
