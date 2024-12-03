/**
 * Copyright 2024 Zyron Official.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.zyron.filetree.utils

import android.os.Handler
import android.os.Looper

/** Utility object providing helper methods for running tasks on the main thread. */
object Utils {
  private val handler = Handler(Looper.getMainLooper())

  /**
   * Posts a Runnable to be executed on the main (UI) thread.
   *
   * @param runnable The Runnable to be executed on the UI thread.
   */
  fun runOnUiThread(runnable: Runnable) {
    handler.post(runnable)
  }
}
