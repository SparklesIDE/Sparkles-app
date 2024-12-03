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
package com.zyron.filetree.events

import java.io.File

/** Interface to listen for events within a file tree, including clicks and updates. */
interface FileTreeEventListener {

  /**
   * Called when a file is clicked.
   *
   * @param file The file that was clicked.
   */
  fun onFileClick(file: File)

  /**
   * Called when a folder is clicked.
   *
   * @param folder The folder that was clicked.
   */
  fun onFolderClick(folder: File)

  /**
   * Called when a file is long-clicked.
   *
   * @param file The file that was long-clicked.
   * @return True if the long-click event was handled, false otherwise.
   */
  fun onFileLongClick(file: File): Boolean

  /**
   * Called when a folder is long-clicked.
   *
   * @param folder The folder that was long-clicked.
   * @return True if the long-click event was handled, false otherwise.
   */
  fun onFolderLongClick(folder: File): Boolean

  /**
   * Called when the file tree view is updated.
   *
   * @param startPosition The starting position of the update.
   * @param itemCount The number of items updated.
   */
  fun onFileTreeViewUpdated(startPosition: Int, itemCount: Int)
}
