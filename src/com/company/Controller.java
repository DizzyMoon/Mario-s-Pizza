package com.company;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

  private Scanner sc = new Scanner(System.in);
  private PizzaMenu pizzaMenu = new PizzaMenu();
  private OrderList orderList = new OrderList();
  private String historyFilePath = "OrdreHistorik.csv";

  private File historyFile = new File(historyFilePath);

  private boolean running = true;

  public void run() {
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

  private void showOptions() {
    System.out.println();
    System.out.println("1: Vis menukort");
    System.out.println("2: Vis ordrer");
    System.out.println("3: Vis ordrehistorik");
    System.out.println("4: Tilføj ordre");
    System.out.println("5: Ændr ordre status");
    System.out.println("6: Afslut");
    System.out.println();
  }

  private void showMenu() {
    System.out.println();
    for (Pizza pizza : pizzaMenu.getPizzas()) {
      System.out.print(pizza);
      System.out.print(createSpacing(pizza.toString(), 80) + " ");
      System.out.println(pizza.getPrice() + ",-");
    }
  }

  private void showOrders() {
    orderList = sortOrderList(orderList);
    System.out.println();
    System.out.println("Aktive ordre:");
    if (orderList.getOrders().size() > 0) {
      for (int i = 0; i < orderList.getOrders().size(); i++) {
        System.out.print(i + 1 + ": " + orderList.getOrders().get(i).getDateOfMonth() + "/" + orderList.getOrders().get(i).getMonth() + " kl. " + String.format("%02d", orderList.getOrders().get(i).getHour()) + ":" + String.format("%02d", orderList.getOrders().get(i).getMinute()) + " - Pizza #" + orderList.getOrders().get(i).getPizza().getNumber() + ": " + orderList.getOrders().get(i).getPizza().getName() + " - Note: " + orderList.getOrders().get(i).getNote() + " - Status: " + orderList.getOrders().get(i).getStatus().name());
        if (orderList.getOrders().get(i).getHasPickupTime()) {
          System.out.println(" - Afhentningstidspunkt: " + String.format("%02d", orderList.getOrders().get(i).getTakeawayHour()) + ":" + String.format("%02d", orderList.getOrders().get(i).getTakeawayMinute()));
        } else {
          System.out.println();
        }
      }
    } else {
      System.out.println("Der er ingen aktive ordre.");
    }
  }

  private void showOrderHistory() {
    try {
      List<List<String>> data = new ArrayList<>();
      FileReader fr = new FileReader(historyFile);
      BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();
      while (line != null) {
        List<String> lineData = Arrays.asList(line.split(","));
        data.add(lineData);
        line = br.readLine();
      }

      System.out.println();
      System.out.println("Ordre historik:");
      int income = 0;
      for (List<String> list : data) {
        for (int i = 0; i < list.size(); i++) {
          System.out.print(list.get(i));
          switch (i) {
            case 0 -> System.out.print("/");
            case 2 -> System.out.print(":");
            case 5 -> {
              System.out.print(",- ");
              if (list.get(6).equals("PAID")) {
                income += Integer.parseInt(list.get(i));
              }
            }
            default -> System.out.print(" ");
          }
        }
        System.out.println();
      }
      System.out.println("Total fortjeneste: " + income + "kr.");
      br.close();
    } catch (Exception e) {
      System.out.print(e);
      System.out.println();
    }
  }



  private void addOrder() {
    showMenu();

    int pizzaNum = -1;
    pizzaNum = pickPizza(pizzaNum);

    System.out.print("Skriv note: ");
    String pizzaNote = sc.nextLine();

    boolean pickedYesNo = false;
    while (!pickedYesNo) {
      System.out.print("Vælg afhentningstidspunkt? (j/n): ");
      String yesNo = sc.nextLine();

      switch (yesNo) {
        case "j" -> {
          pickedYesNo = true;
          int hourInt = -1;
          int minInt = -1;
          hourInt = pickHour(hourInt);
          minInt = pickMin(minInt);
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

  private int pickPizza(int pizzaNum) {
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
    return pizzaNum;
  }

  private int pickHour(int hourInt) {
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
    return hourInt;
  }

  private int pickMin(int minInt) {
    boolean pickedMin = false;
    while (!pickedMin) {
      System.out.print("Vælg minut: ");
      String minString = sc.nextLine();
      if (tryParseInt(minString)) {
        minInt = Integer.parseInt(minString);
      } else {
        System.out.println("Ugyldig kommando.");
      }
      if ((minInt >= 0) && (minInt <= 59)) {
        pickedMin = true;
      }
    }
    return minInt;
  }

  public void setOrderStatus() {
    showOrders();

    int orderInput = -1;
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

    if ((orderInput - 1 >= 0) && (orderInput <= orderList.getOrders().size())) {
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
                    orderList.getOrders().get(orderInput - 1).setStatus(OrderStatus.ORDERED);
                    System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Bestilt'.");
                }
                case "2" -> {
                    orderList.getOrders().get(orderInput - 1).setStatus(OrderStatus.COOKING);
                    System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Tilberedes'.");
                }
                case "3" -> {
                    orderList.getOrders().get(orderInput - 1).setStatus(OrderStatus.FINISHED);
                    System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Færdig'.");
                }
                case "4" -> {
                    orderList.getOrders().get(orderInput - 1).setStatus(OrderStatus.PAID);
                    addOrderToHistory(orderInput);
                    orderList.removeOrder(orderList.getOrders().get(orderInput - 1));
                    System.out.println("Ordre nummer " + orderInput + " er nu sat til 'Betalt' og fjernet fra ordre listen.");
                }
                case "5" -> {
                    orderList.getOrders().get(orderInput - 1).setStatus(OrderStatus.CANCELLED);
                    addOrderToHistory(orderInput);
                    orderList.removeOrder(orderList.getOrders().get(orderInput - 1));
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

    private void addOrderToHistory(int orderInput) {
        ArrayList<String[]> ordreHistorik = new ArrayList<>();
        try {
            FileReader fr = new FileReader(historyFile);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArray;
            while ((line = br.readLine()) != null) {
                tempArray = line.split(",");
                ordreHistorik.add(tempArray);
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    ordreHistorik.add(new String[]{Integer.toString(orderList.getOrders().get(orderInput - 1).getDateOfMonth()), Integer.toString(orderList.getOrders().get(orderInput - 1).getMonth()), Integer.toString(orderList.getOrders().get(orderInput - 1).getHour()), Integer.toString(orderList.getOrders().get(orderInput - 1).getMinute()), orderList.getOrders().get(orderInput - 1).getPizza().getName(), Integer.toString(orderList.getOrders().get(orderInput - 1).getPizza().getPrice()), orderList.getOrders().get(orderInput - 1).getStatus().name()});
    try {
      convertToCSV(ordreHistorik);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public OrderList sortOrderListByHour(OrderList input) {
    ArrayList<Order> sortedHours = input.getOrders();
    int sortedNums;
    boolean sorted = false;

    while (!sorted) {
      sortedNums = 0;
      for (int i = sortedHours.size() - 1; i > 0; i--) {
        if (!sortedHours.get(i).getHasPickupTime()) {
          if (sortedHours.get(i).getHour() < sortedHours.get(i - 1).getHour()) {
            Order temp = sortedHours.get(i);
            sortedHours.set(i, sortedHours.get(i - 1));
            sortedHours.set(i - 1, temp);
          } else sortedNums++;
        } else {
          if (sortedHours.get(i).getTakeawayHour() < sortedHours.get(i - 1).getTakeawayHour()) {
            Order temp = sortedHours.get(i);
            sortedHours.set(i, sortedHours.get(i - 1));
            sortedHours.set(i - 1, temp);
          } else sortedNums++;
        }
      }
      if (sortedNums == sortedHours.size() - 1) {
        sorted = true;
      }
    }
    return new OrderList(sortedHours);
  }

  public OrderList sortOrderListByMinute(OrderList input) {
    ArrayList<Order> sortedMinutes = input.getOrders();

    boolean sorted = false;

    while (!sorted) {
      int sortedNums = 0;
      for (int i = sortedMinutes.size() - 1; i > 0; i--) {
        if (!sortedMinutes.get(i).getHasPickupTime()) {
          if (sortedMinutes.get(i).getMinute() < sortedMinutes.get(i - 1).getMinute()) {
            Order temp = sortedMinutes.get(i);
            sortedMinutes.set(i, sortedMinutes.get(i - 1));
            sortedMinutes.set(i - 1, temp);
          } else sortedNums++;
        } else {
          if (sortedMinutes.get(i).getTakeawayMinute() < sortedMinutes.get(i - 1).getTakeawayMinute()) {
            Order temp = sortedMinutes.get(i);
            sortedMinutes.set(i, sortedMinutes.get(i - 1));
            sortedMinutes.set(i - 1, temp);
          } else sortedNums++;
        }
      }
      if (sortedNums == sortedMinutes.size() - 1) {
        sorted = true;
      }
    }
    return new OrderList(sortedMinutes);
  }

  public OrderList sortOrderList(OrderList input) {
    OrderList orders = sortOrderListByHour(input);
    return sortOrderListByMinute(orders);
  }
    /*
    public OrderList sortOrderList(OrderList input) {
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
        try (PrintWriter pw = new PrintWriter(historyFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
