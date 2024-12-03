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
package com.zyron.filetree

import android.content.Context
import android.util.Log
import com.zyron.filetree.datamodel.FileTreeNode
import com.zyron.filetree.map.ConcurrentFileMap
import com.zyron.filetree.map.FileMapManager
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlinx.coroutines.*

/** Listener interface to notify when the file tree is updated. */
interface FileTreeAdapterUpdateListener {
  fun onFileTreeUpdated(startPosition: Int, itemCount: Int)
}

/**
 * FileTree represents a file tree structure with functionality to expand and collapse nodes.
 *
 * @property context The application context.
 * @property rootDirectory The root directory path of the file tree.
 */
class FileTree(private val context: Context, private val rootDirectory: String) {

  private val nodes: MutableList<FileTreeNode> = mutableListOf()
  private val expandedNodes: MutableSet<FileTreeNode> = mutableSetOf()
  private var adapterUpdateListener: FileTreeAdapterUpdateListener? = null
  private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
  private var loading = false

  /**
   * Sets the listener for adapter updates.
   *
   * @param listener The listener to be notified of updates.
   */
  fun setAdapterUpdateListener(listener: FileTreeAdapterUpdateListener) {
    this.adapterUpdateListener = listener
  }

  init {
    val file = File(rootDirectory)
    val rw = file.canRead() && file.canWrite()
    if (!file.exists() || !rw) {
      Log.e(
        this::class.java.simpleName,
        "The provided path: $rootDirectory is invalid or does not exist.",
      )
      Log.d(this::class.java.simpleName, "Continuing anyway...")
    }

    scope.coroutineContext[Job]?.invokeOnCompletion { exception ->
      if (exception is CancellationException) {
        Log.d(this::class.java.simpleName, "FileTree operation was cancelled.")
      }
    }

    scope.launch {
      try {
        if (!loading) {
          loading = true
          val rootPath = Paths.get(rootDirectory)
          val rootNode = FileTreeNode(rootPath.toFile())
          addNode(rootNode)
          FileMapManager.startFileMapping(nodes)
          expandNode(rootNode)
        }
      } catch (e: Exception) {
        Log.e(this::class.java.simpleName, "Error initializing FileTree: ${e.localizedMessage}", e)
      }
    }
  }

  /**
   * Returns the list of all nodes in the file tree.
   *
   * @return List of FileTreeNode objects.
   */
  fun getNodes(): List<FileTreeNode> = nodes

  /**
   * Returns the set of expanded nodes in the file tree.
   *
   * @return Set of expanded FileTreeNode objects.
   */
  fun getExpandedNodes(): Set<FileTreeNode> = expandedNodes

  /**
   * Adds a node to the file tree and notifies the adapter.
   *
   * @param node The node to add.
   * @param parent The parent node, if any.
   */
  private suspend fun addNode(node: FileTreeNode, parent: FileTreeNode? = null) {
    node.parent = parent
    nodes.add(node)
    withContext(Dispatchers.Main) {
      adapterUpdateListener?.onFileTreeUpdated(nodes.indexOf(node), 1)
    }
  }

  /**
   * Expands a node to display its children.
   *
   * @param node The node to expand.
   */
  fun expandNode(node: FileTreeNode) {
    if (!node.isExpanded && Files.isDirectory(node.file.toPath())) {
      node.isExpanded = true
      scope.launch {
        try {
          expandedNodes.add(node)

          val newNodes: List<FileTreeNode>? =
            synchronized(ConcurrentFileMap.concurrentFileMap) {
              ConcurrentFileMap.concurrentFileMap[node] ?: node.sortNode()
            }

          newNodes?.let {
            if (it.isNotEmpty()) {
              val insertIndex = nodes.indexOf(node) + 1
              node.childrenStartIndex = insertIndex
              node.childrenEndIndex = insertIndex + it.size
              nodes.addAll(insertIndex, it)
              withContext(Dispatchers.Main) {
                adapterUpdateListener?.onFileTreeUpdated(node.childrenStartIndex, it.size)
              }
            }
          }
        } catch (e: Exception) {
          Log.e(this::class.java.simpleName, "Error expanding node: ${e.localizedMessage}", e)
        }
      }
    }
  }

  /**
   * Collects all children of a node recursively.
   *
   * @param node The parent node.
   * @param nodesToRemove The list to which all children will be added.
   */
  private fun collectAllChildren(node: FileTreeNode, nodesToRemove: MutableList<FileTreeNode>) {
    val children = nodes.filter { it.parent == node }
    nodesToRemove.addAll(children)
    children.forEach { childNode -> collectAllChildren(childNode, nodesToRemove) }
  }

  /**
   * Collapses a node, hiding its children.
   *
   * @param node The node to collapse.
   */
  fun collapseNode(node: FileTreeNode) {
    if (node.isExpanded) {
      node.isExpanded = false
      scope.launch {
        try {
          expandedNodes.remove(node)
          val nodesToRemove = mutableListOf<FileTreeNode>()
          collectAllChildren(node, nodesToRemove)
          nodesToRemove.forEach { childNode ->
            childNode.isExpanded = false
            expandedNodes.remove(childNode)
          }
          nodes.removeAll(nodesToRemove)
          withContext(Dispatchers.Main) {
            adapterUpdateListener?.onFileTreeUpdated(nodes.indexOf(node), nodesToRemove.size)
          }
        } catch (e: Exception) {
          Log.e(this::class.java.simpleName, "Error collapsing node: ${e.localizedMessage}", e)
        }
      }
    }
  }

  /** Cancels all coroutines related to this FileTree. */
  fun cancelAllCoroutines() {
    scope.cancel()
  }

  /** Cleans up resources when the FileTree is destroyed. */
  fun onDestroy() {
    scope.coroutineContext[Job]?.cancelChildren()
  }
}
