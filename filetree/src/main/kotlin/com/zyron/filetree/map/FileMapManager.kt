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
package com.zyron.filetree.map

import android.util.Log
import com.zyron.filetree.datamodel.FileTreeNode
import java.util.concurrent.Executors
import java.util.concurrent.Future

/** Manages the mapping of file nodes using a single-threaded executor. */
object FileMapManager {
  private var fileCacheFuture: Future<*>? = null
  private val executor = Executors.newSingleThreadExecutor()

  /** Stops the current file mapping task, if one is running. */
  fun stopFileMapping() {
    fileCacheFuture?.cancel(true)
    fileCacheFuture = null
    Log.i(this::class.java.simpleName, "FileMapping stopped")
  }

  /**
   * Starts the file mapping process on a separate thread.
   *
   * @param nodes The list of root nodes to process.
   * @param priority Optional priority setting for the mapping thread.
   */
  fun startFileMapping(nodes: List<FileTreeNode>, priority: Int? = null) {
    if (fileCacheFuture == null) {
      fileCacheFuture =
        executor
          .submit {
            try {
              val fileCache = ConcurrentFileMap(nodes)
              fileCache.run()
            } catch (e: InterruptedException) {
              Thread.currentThread().interrupt()
            } catch (e: Exception) {
              Log.e(this::class.java.simpleName, "Error in FileMapping", e)
            }
          }
          .apply { priority?.let { (this as Thread).priority = it } }
      Log.i(this::class.java.simpleName, "FileMapping started")
    } else {
      Log.e(this::class.java.simpleName, "FileMapping is already running; this might cause issues")
    }
  }
}
