package com.company;

import java.time.LocalDateTime;
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

      System.out.print("Vælg handling: ");
      String input = sc.nextLine();

      switch (input) {
        case "1" -> showMenu();
        case "2" -> showOrders();
        case "3" -> showOrderHistory();
        case "4" -> addOrder();
        case "5" -> setOrderStatus();
        case "6" -> exit();
        default -> System.out.println("Ugyldig kommando.");
      }
    }
  }

  public void showOptions() {
    System.out.println();
    System.out.println("1: Vis menukort");
    System.out.println("2: Vis ordrer");
    System.out.println("3: Vis ordrehistorik");
    System.out.println("4: Tilføj ordre");
    System.out.println("5: Ændr ordre status");
    System.out.println("6: Afslut");
    System.out.println();
  }

  public void showMenu() {
    System.out.println();
    for (Pizza pizza : pizzaMenu.getPizzas()){
      System.out.print(pizza);
      System.out.print(createSpacing(pizza.toString(), 80) + " ");
      System.out.println(pizza.getPrice() + ",-");
    }
  }

  public void showOrders() {
    System.out.println();
    System.out.println("Aktive ordre:");
    if (orderList.getOrders().size() > 0) {
      for (int i = 0; i < orderList.getOrders().size(); i++) {
        System.out.println(i+1 + ": " + orderList.getOrders().get(i).getDateOfMonth() + "/" + orderList.getOrders().get(i).getMonth() + " kl. " + String.format("%02d", orderList.getOrders().get(i).getHour()) + ":" + String.format("%02d", orderList.getOrders().get(i).getMinute()) + " - Pizza #" + orderList.getOrders().get(i).getPizza().getNumber() + ": " + orderList.getOrders().get(i).getPizza().getName() + " - Note: " + orderList.getOrders().get(i).getNote() + " - Status: " + orderList.getOrders().get(i).getStatus().name());
      }
    } else {
      System.out.println("Der er ingen aktive ordre.");
    }
  }

  public void showOrderHistory() {
    System.out.println();
    System.out.println("Ordre historik:");
    int totalEarnings = 0;
    if (orderHistory.getOrders().size() > 0) {
      for (int i = 0; i < orderHistory.getOrders().size(); i++) {
        System.out.print(i+1 + ": " + orderHistory.getOrders().get(i).getDateOfMonth() + "/" + orderHistory.getOrders().get(i).getMonth() + " kl. " + String.format("%02d", orderHistory.getOrders().get(i).getHour()) + ":" + String.format("%02d", orderHistory.getOrders().get(i).getMinute()) + " - Pizza #" + orderHistory.getOrders().get(i).getPizza().getNumber() + ": " + orderHistory.getOrders().get(i).getPizza().getName() + " - Note: " + orderHistory.getOrders().get(i).getNote() + " - Status: " + orderHistory.getOrders().get(i).getStatus().name());
        if (orderHistory.getOrders().get(i).getStatus() == OrderStatus.PAID) {
          System.out.println(" - Fortjeneste: " + orderHistory.getOrders().get(i).getPizza().getPrice() + "kr.");
          totalEarnings += orderHistory.getOrders().get(i).getPizza().getPrice();
        } else {
          System.out.println();
        }
      }
      System.out.println("Total fortjeneste: " + totalEarnings + "kr.");
    } else {
      System.out.println("Der er ingen ordre i historikken.");
    }
  }

  public void addOrder() {
    showMenu();

    int pizzaNum = 0;
    boolean intFound = false;
    while (intFound == false) {
      System.out.println();
      System.out.print("Bestil pizza nummer: ");
      String pizzaNumStr = sc.nextLine();
      if (tryParseInt(pizzaNumStr)) {
        pizzaNum = Integer.parseInt(pizzaNumStr);
        intFound = true;
      } else {
        System.out.println("Ugyldig kommando.");
      }
    }
    System.out.print("Skriv note: ");
    String pizzaNote = sc.nextLine();

    orderList.addOrder(new Order(pizzaMenu.getPizzas().get(pizzaNum - 1), pizzaNote, LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), OrderStatus.ORDERED));
    System.out.println("Pizza #" + pizzaNum + " " + pizzaMenu.getPizzas().get(pizzaNum - 1).getName() + " er blevet bestilt.");
  }

  public void setOrderStatus() {
    showOrders();

    int orderInput = 0;
    boolean intFound = false;
    while (intFound == false) {
      System.out.println();
      System.out.print("Ændr status på ordre nummer: ");
      String numStr = sc.nextLine();
      if (tryParseInt(numStr)) {
        orderInput = Integer.parseInt(numStr);
        intFound = true;
      } else {
        System.out.println("Ugyldig kommando.");
      }
    }

    if ((orderInput-1 >= 0) && (orderInput <= orderList.getOrders().size())) {
      System.out.println();
      System.out.println("Status valg: ");
      System.out.println("1: Bestilt");
      System.out.println("2: Tilberedes");
      System.out.println("3: Færdig");
      System.out.println("4: Betalt");
      System.out.println("5: Annulleret");
      System.out.println();
      System.out.print("Vælg ny status: ");

      String statusInput = sc.nextLine();

      switch (statusInput) {
        case "1" -> {
          orderList.getOrders().get(orderInput-1).setStatus(OrderStatus.ORDERED);
          System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Bestilt'.");
        }
        case "2" -> {
          orderList.getOrders().get(orderInput-1).setStatus(OrderStatus.COOKING);
          System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Tilberedes'.");
        }
        case "3" -> {
          orderList.getOrders().get(orderInput-1).setStatus(OrderStatus.FINISHED);
          System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Færdig'.");
        }
        case "4" -> {
          orderList.getOrders().get(orderInput-1).setStatus(OrderStatus.PAID);
          orderHistory.addOrder(orderList.getOrders().get(orderInput-1));
          orderList.removeOrder(orderList.getOrders().get(orderInput-1));
          System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Betalt' og fjernet fra ordre listen.");
        }
        case "5" -> {
          orderList.getOrders().get(orderInput-1).setStatus(OrderStatus.CANCELLED);
          orderHistory.addOrder(orderList.getOrders().get(orderInput-1));
          orderList.removeOrder(orderList.getOrders().get(orderInput-1));
          System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Annulleret' og fjernet fra ordre listen.");
        }
        default -> {
          System.out.println("Status findes ikke, vælg mellem 1-5.");
        }
      }
    } else {
      System.out.println("Ordre ikke fundet.");
    }
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

  private boolean tryParseInt(String str) {
    try {
      Integer.parseInt(str);
    } catch (Exception E) {
      return false;
    }
    return true;
  }
}
