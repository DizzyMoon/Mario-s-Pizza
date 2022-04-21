package com.company;

import java.util.Calendar;
import java.util.Scanner;

public class Controller {

  private OrderList orderList = new OrderList();
  private OrderHistory orderHistory = new OrderHistory();
  private PizzaMenu pizzaMenu = new PizzaMenu();
  private Calendar cal = Calendar.getInstance();

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
    int input = sc.nextInt();
    if (!(input < orderList.getOrders().size() || input > orderList.getOrders().size())){
      System.out.println(orderList.getOrders().get(input - 1));
    } else{
      System.out.println("Ordren findes ikke");
    }
  }


  public void showOrderHistory() {
    for (Order order : orderList.getOrders()){
      System.out.println(order + " - STATUS: " + String.valueOf(order.getStatus().name()) + ", Bestilt: " + order.getHour() + ":" + order.getMinute() + " d. " + order.getDateOfMonth() + "/" + order.getMonth());
    }
  }

  public void addOrder() {
    int pizzaNum = sc.nextInt();
    sc.nextLine();
    String pizzaDesc = sc.nextLine();

    orderList.addOrder(new Order(pizzaMenu.getPizzas().get(pizzaNum - 1), pizzaDesc, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH + 1), cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), OrderStatus.ORDERED));
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
