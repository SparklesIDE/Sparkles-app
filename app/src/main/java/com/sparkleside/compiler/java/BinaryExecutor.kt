package com.sparkleside.compiler.java;

import java.io.PrintWriter
import java.io.StringWriter
import java.util.Scanner

class BinaryExecutor {

  private val mProcess = ProcessBuilder()
  private val mWriter = StringWriter()

  fun setCommands(commands: List<String>) {
    mProcess.command(commands)
  }

  fun execute(): String {
    try {
      val process = mProcess.start()
      val scanner = Scanner(process.errorStream)
      while (scanner.hasNextLine()) {
        mWriter.append(scanner.nextLine())
        mWriter.append(System.lineSeparator())
      }
      process.waitFor()
    } catch (e: Exception) {
      e.printStackTrace(PrintWriter(mWriter))
    }
    return mWriter.toString()
  }

  fun getLogs(): String {
    return mWriter.toString()
  }
}
