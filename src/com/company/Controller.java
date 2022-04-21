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
    pizzaMenu.createPizzas();

    while (running) {
      showOptions();

      int input = sc.nextInt();

      switch (input) {
        case 1 -> showMenu();
        case 2 -> showOrder();
        case 3 -> showOrderHistory();
        case 4 -> addOrder();
        case 5 -> setOrderStatus();
        case 6 -> cancelOrder();
        case 7 -> exit();
      }
    }

  }


  public void showOptions() {
    System.out.println();
    System.out.println("1: Vis menukort");
    System.out.println("2: Vis bestilling");
    System.out.println("3: Vis bestillingshistorik");
    System.out.println("4: Tilføj bestilling");
    System.out.println("5: Ændr bestilling");
    System.out.println("6: Slet bestilling");
    System.out.println("7: Afslut");
    System.out.println();
  }

  public void showMenu() {
    for (Pizza pizza : pizzaMenu.getPizzas()){
      System.out.print(pizza);
      System.out.print(createSpacing(pizza.toString(), 80) + " ");
      System.out.println(pizza.getPrice() + ",-");

    }

  }

  public void showOrder() {

  }


  public void showOrderHistory() {
    for (Order order : orderList.getOrders()){
      System.out.print(order);
    }
  }

  public void addOrder() {
    int input = sc.nextInt();
    Pizza pizza1 = new Pizza(1, "Hawaii", new ArrayList<String>(Arrays.asList("Ananas")), 64);
    Order order1 = new Order(pizza1, "Uden brød", 15, 3, 5, 4, OrderStatus.ORDERED);
    orderList.addOrder(order1);
  }

  public void setOrderStatus() {
    String spacing = createSpacing("Hawaii pizza", 50);
    System.out.println("Hawaii Pizza" + spacing);
  }

  public void cancelOrder() {
    System.out.println("Slet bestillingsnummer:");
    int input = sc.nextInt();

    if (!(input < orderList.getOrders().size() || input > orderList.getOrders().size())) {
      orderList.removeOrder(orderList.getOrders().get(input - 1));
    } else
      System.out.println("Bestillingsnummer findes ikke");
    }

  public void exit(){
    running = false;
  }

  public String createSpacing(String initiate, int amount) {
    StringBuilder sb = new StringBuilder();
    for (int i = amount - initiate.length(); i != 0; i--) {
      sb.append(".");
    }
    sb.append(":");
    String spacing = String.valueOf(sb);
    return spacing;
  }

}
