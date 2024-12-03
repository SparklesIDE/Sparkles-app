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
package com.zyron.filetree.datamodel

import java.io.File

/**
 * Represents a node in a file tree, corresponding to a file or directory.
 *
 * @property file The file or directory represented by this node.
 * @property parent The parent node of this node in the file tree.
 * @property level The depth level of this node in the file tree.
 */
data class FileTreeNode(var file: File, var parent: FileTreeNode? = null, var level: Int = 0) {
  var isExpanded: Boolean = false
  var childrenStartIndex: Int = 0
  var childrenEndIndex: Int = 0
  var childrenLoaded: Boolean = false

  /**
   * Sorts the children of this node, separating directories from files and sorting them
   * alphabetically.
   *
   * @return A list of FileTreeNode objects representing the sorted children.
   */
  fun sortNode(): List<FileTreeNode> {
    val children =
      file
        .listFiles()
        ?.asSequence()
        ?.partition { it.isDirectory }
        ?.let { (directories, files) ->
          (directories.sortedBy { it.name.lowercase() } + files.sortedBy { it.name.lowercase() })
        } ?: emptyList()
    return children.map { childFile ->
      FileTreeNode(file = childFile, parent = this, level = level + 1)
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false
    other as FileTreeNode
    return file.absolutePath == other.file.absolutePath
  }

  override fun hashCode(): Int {
    return file.absolutePath.hashCode()
  }
}
