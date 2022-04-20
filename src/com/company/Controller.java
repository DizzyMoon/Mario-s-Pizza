package com.company;

import java.time.LocalDateTime;

public class Controller {

  public void run(){
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
