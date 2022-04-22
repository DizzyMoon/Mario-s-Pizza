package com.company;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        System.out.println(i+1 + ": " + sortOrderList(orderList).getOrders().get(i).getDateOfMonth() + "/" + sortOrderList(orderList).getOrders().get(i).getMonth() + " kl. " + String.format("%02d", sortOrderList(orderList).getOrders().get(i).getHour()) + ":" + String.format("%02d", sortOrderList(orderList).getOrders().get(i).getMinute()) + " - Pizza #" + sortOrderList(orderList).getOrders().get(i).getPizza().getNumber() + ": " + sortOrderList(orderList).getOrders().get(i).getPizza().getName() + " - Note: " + sortOrderList(orderList).getOrders().get(i).getNote() + " - Status: " + sortOrderList(orderList).getOrders().get(i).getStatus().name());
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

      boolean pickedYesNo = false;
      while (!pickedYesNo) {
          System.out.print("Vælg afhentningstidspunkt? (j/n): ");
          String yesNo = sc.nextLine();

          switch (yesNo) {
              case "j" -> {
                  int minInt = -1;
                  int hourInt = -1;
                  String hourString;
                  boolean pickedHour = false;
                  while (!pickedHour) {
                      System.out.print("Vælg time: ");
                      hourString = sc.nextLine();

                      if (tryParseInt(hourString)) {
                          hourInt = Integer.parseInt(hourString);
                      } else {
                          System.out.println("Ugyldig kommando.");
                      }
                      if ((hourInt >= 0) && (hourInt <= 23)) {
                          pickedHour = true;
                      }
                  }
                  boolean pickedMin = false;
                  while (!pickedMin) {
                      System.out.print("Vælg minut: ");
                      String minString = sc.nextLine();
                      if (tryParseInt(minString)) {
                          minInt = Integer.parseInt(minString);
                      } else {
                          System.out.println("Ugyldig kommando.");
                      }
                      if ((minInt > 0) && (minInt < 59)) {
                          pickedMin = true;
                      }
                  }
                  pickedYesNo = true;
                  orderList.addOrder(new Order(pizzaMenu.getPizzas().get(pizzaNum - 1), pizzaNote, LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), OrderStatus.ORDERED, hourInt, minInt));
                  System.out.println("Pizza #" + pizzaNum + " " + pizzaMenu.getPizzas().get(pizzaNum - 1).getName() + " er blevet bestilt.");
              }
              case "n" -> {
                  pickedYesNo = true;
                  orderList.addOrder(new Order(pizzaMenu.getPizzas().get(pizzaNum - 1), pizzaNote, LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), OrderStatus.ORDERED));
                  System.out.println("Pizza #" + pizzaNum + " " + pizzaMenu.getPizzas().get(pizzaNum - 1).getName() + " er blevet bestilt.");
              }
              default -> {
                  System.out.println("Ugyldig kommando.");
              }
          }
      }
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
          System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Betalt' og fjernet fra ordre listen.");
          ArrayList<String[]> ordreData = new ArrayList<>();
          ordreData.add(new String[] { Integer.toString(orderList.getOrders().get(orderInput - 1).getDateOfMonth()), Integer.toString(orderList.getOrders().get(orderInput - 1).getMonth()), Integer.toString(orderList.getOrders().get(orderInput - 1).getHour()), Integer.toString(orderList.getOrders().get(orderInput - 1).getMinute()), orderList.getOrders().get(orderInput - 1).getPizza().getName(), Integer.toString(orderList.getOrders().get(orderInput - 1).getPizza().getPrice()), orderList.getOrders().get(orderInput - 1).getStatus().name() });
          try {
            convertToCSV(ordreData);
          } catch (IOException e) {
            e.printStackTrace();
          }
          orderList.removeOrder(orderList.getOrders().get(orderInput-1));
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

    public OrderList sortOrderListByHour(OrderList input) {
        ArrayList<Order> sortedHours = input.getOrders();
        int sortedNums;
        boolean sorted = false;

        while (!sorted) {
            sortedNums = 0;
            for (int i = sortedHours.size() - 1; i > 0; i--) {
                if (sortedHours.get(i).getHour() < sortedHours.get(i - 1).getHour()) {
                    Order temp = sortedHours.get(i);
                    sortedHours.set(i, sortedHours.get(i - 1));
                    sortedHours.set(i - 1, temp);
                } else sortedNums++;
            }
            if (sortedNums == sortedHours.size() - 1) {
                sorted = true;
            }
        } return new OrderList(sortedHours);
    }

    public OrderList sortOrderListByMinute(OrderList input){
        ArrayList<Order> sortedMinutes = input.getOrders();

        boolean sorted = false;

        while (!sorted) {
            int sortedNums = 0;
            for (int i = sortedMinutes.size() - 1; i > 0; i--) {
                if (sortedMinutes.get(i).getMinute() < sortedMinutes.get(i - 1).getMinute()) {
                    Order temp = sortedMinutes.get(i);
                    sortedMinutes.set(i, sortedMinutes.get(i - 1));
                    sortedMinutes.set(i - 1, temp);
                } else sortedNums++;
            }
            if (sortedNums == sortedMinutes.size() - 1) {
                sorted = true;
            }
        }
        return new OrderList(sortedMinutes);
    }

    public OrderList sortOrderList(OrderList input){
      OrderList orders = sortOrderListByHour(input);
      return sortOrderListByMinute(orders);
    }


   /* public OrderList sortOrderList(OrderList input) {
        ArrayList<Order> sortedHours = input.getOrders();
        ArrayList<Order> sortedMinutes;

        int sortedNums;
        boolean sorted = false;

        while (!sorted) {
            sortedNums = 0;
            for (int i = sortedHours.size() - 1; i > 0; i--) {
                if (sortedHours.get(i).getHour() < sortedHours.get(i - 1).getHour()) {
                    Order temp = sortedHours.get(i);
                    sortedHours.set(i, sortedHours.get(i - 1));
                    sortedHours.set(i - 1, temp);
                } else sortedNums++;
            }
            if (sortedNums == sortedHours.size() - 1) {
                sorted = true;
            }
        }

        sortedMinutes = sortedHours;

        sorted = false;

        while (!sorted) {
            sortedNums = 0;
            for (int i = sortedMinutes.size() - 1; i > 0; i--) {
                if (sortedMinutes.get(i).getMinute() < sortedMinutes.get(i - 1).getMinute()) {
                    Order temp = sortedMinutes.get(i);
                    sortedMinutes.set(i, sortedMinutes.get(i - 1));
                    sortedMinutes.set(i - 1, temp);
                } else sortedNums++;
            }
            if (sortedNums == sortedMinutes.size() - 1) {
                sorted = true;
            }
        }
        return new OrderList(sortedMinutes);
    }

    */



    public ArrayList<Integer> sortIntList(ArrayList<Integer> input) {

        ArrayList<Integer> arrayList = input;
        int sortedNums;
        boolean sorted = false;

        while (!sorted) {
            sortedNums = 0;
            for (int i = arrayList.size() - 1; i > 0; i--) {
                if (arrayList.get(i) < arrayList.get(i - 1)) {
                    int temp = arrayList.get(i);
                    arrayList.set(i, arrayList.get(i - 1));
                    arrayList.set(i - 1, temp);
                } else sortedNums++;
            }
            if (sortedNums == arrayList.size() - 1) {
                sorted = true;
            }
        }
        return arrayList;
    }

    public void exit() {
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

  public String convertToCSV(String[] data) {
    return Stream.of(data)
            .map(this::escapeSpecialCharacters)
            .collect(Collectors.joining(","));
  }
  public String escapeSpecialCharacters(String data) {
    String escapedData = data.replaceAll("\\R", " ");
    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
      data = data.replace("\"", "\"\"");
      escapedData = "\"" + data + "\"";
    }
    return escapedData;
  }
  public void convertToCSV(ArrayList<String[]> dataLines) throws IOException {
    File csvOutputFile = new File("src/OrdreHistorik");
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      dataLines.stream()
              .map(this::convertToCSV)
              .forEach(pw::println);
    }
    //assertTrue(csvOutputFile.exists());
  }

}
