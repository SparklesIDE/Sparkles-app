package org.robok.engine.feature.compiler.java;

/*
 *  This file is part of Robok © 2024.
 *
 *  Robok is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Robok is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with Robok.  If not, see <https://www.gnu.org/licenses/>.
 */

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
