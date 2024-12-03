package com.sparkleside.compiler.java;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Scanner;

public class BinaryExecutor {

  private final ProcessBuilder processBuilder = new ProcessBuilder();
  private final StringWriter writer = new StringWriter();

  public void setCommands(List<String> commands) {
    processBuilder.command(commands);
  }

  public String execute() {
    try {
      var process = processBuilder.start();
      var inputScanner = new Scanner(process.getInputStream());
      var errorScanner = new Scanner(process.getErrorStream());

      while (inputScanner.hasNextLine()) {
        writer.append(inputScanner.nextLine());
        writer.append(System.lineSeparator());
      }

      while (errorScanner.hasNextLine()) {
        writer.append(errorScanner.nextLine());
        writer.append(System.lineSeparator());
      }

      process.waitFor();
    } catch (Exception e) {
      e.printStackTrace(new PrintWriter(writer));
    }
    return writer.toString();
  }

  public String getLogs() {
    return writer.toString();
  }
}
