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
package com.zyron.filetree.callback

import androidx.recyclerview.widget.DiffUtil
import com.zyron.filetree.datamodel.FileTreeNode

/** A DiffUtil.ItemCallback implementation for comparing FileTreeNode objects. */
class FileTreeDiffCallback : DiffUtil.ItemCallback<FileTreeNode>() {

  /**
   * Determines if two FileTreeNode items represent the same file by comparing their absolute paths.
   *
   * @param oldItem The previous FileTreeNode item.
   * @param newItem The new FileTreeNode item.
   * @return True if the two items represent the same file, false otherwise.
   */
  override fun areItemsTheSame(oldItem: FileTreeNode, newItem: FileTreeNode): Boolean {
    return oldItem.file.absolutePath == newItem.file.absolutePath
  }

  /**
   * Determines if the contents of two FileTreeNode items are the same.
   *
   * @param oldItem The previous FileTreeNode item.
   * @param newItem The new FileTreeNode item.
   * @return True if the contents of the two items are the same, false otherwise.
   */
  override fun areContentsTheSame(oldItem: FileTreeNode, newItem: FileTreeNode): Boolean {
    return oldItem == newItem
  }
}
