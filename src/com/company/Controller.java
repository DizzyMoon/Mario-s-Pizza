package com.company;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Controller {

  public void run(){
    Scanner sc = new Scanner(System.in);
    int input = sc.nextInt();

    switch (input) {
      case 1 -> showMenu();
      case 2 -> showOrder();
      case 3 -> showOrderHistory();
      case 4 -> addOrder();
      case 5 -> setOrderStatus();
    }

  }

  public void showMenu() {

  }

  public void showOrder() {

  }

  public void showOrderHistory() {

  }

  public void addOrder() {

  }

  public void setOrderStatus() {
    String spacing = createSpacing("Hawaii pizza");
    System.out.println("Hawaii Pizza" + spacing);
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
