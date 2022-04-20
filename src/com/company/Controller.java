package com.company;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Controller {

  public void run(){

    boolean running = true;

    while (running) {
      Scanner sc = new Scanner(System.in);
      String input = sc.nextLine();
      switch (input) {
        case "menu" -> {

        }
        case "orders" -> {

        }
      }
    }
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
