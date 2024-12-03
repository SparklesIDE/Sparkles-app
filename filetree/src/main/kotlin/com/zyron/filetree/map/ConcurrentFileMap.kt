/**
 * Copyright 2024 Zyron Official.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zyron.filetree.map

import com.zyron.filetree.datamodel.FileTreeNode
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Manages a cache of file nodes for efficient access and manipulation using concurrency.
 *
 * @param nodes A list of root nodes to be processed and cached.
 * @param maxSize The maximum size of the cache before trimming.
 */
class ConcurrentFileMap(private val nodes: List<FileTreeNode>, private val maxSize: Int = 100) : Runnable {

    companion object {
        val concurrentFileMap: MutableMap<FileTreeNode, List<FileTreeNode>> = ConcurrentHashMap()
    }

    private val cache = ConcurrentHashMap<FileTreeNode, List<FileTreeNode>>(maxSize)
    private val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())

    init {
        // Schedule cache trimming at fixed intervals
        executor.scheduleAtFixedRate(::trimCache, 10, 10, TimeUnit.SECONDS)
    }

    /**
     * Retrieves a list of child nodes for a given file node from the cache.
     *
     * @param node The parent file node.
     * @return A list of child file nodes, or null if the node is not in the cache.
     */
    fun get(node: FileTreeNode): List<FileTreeNode>? {
        return cache[node]
    }

    /**
     * Adds a file node and its children to the cache.
     *
     * @param node The parent file node.
     * @param result The list of child file nodes.
     */
    fun put(node: FileTreeNode, result: List<FileTreeNode>) {
        cache[node] = result
        if (cache.size > maxSize) {
            trimCache()
        }
    }

    /**
     * Clears the entire cache.
     */
    fun clear() {
        cache.clear()
    }

    /**
     * Processes a list of file nodes asynchronously to populate the cache.
     *
     * @param nodes The list of file nodes to process.
     */
    private fun processNodes(nodes: List<FileTreeNode>) {
        val futures = mutableListOf<Future<*>>()
        for (node in nodes) {
            val future = executor.submit {
                val nodeList = node.sortNode()
                put(node, nodeList)
                processNodes(nodeList)
            }
            futures.add(future)
        }
        futures.forEach { it.get() }
    }

    /**
     * Trims the cache to the specified maximum size by removing excess entries.
     */
    private fun trimCache() {
        if (cache.size > maxSize) {
            val keysToRemove = cache.keys.take(cache.size - maxSize)
            for (key in keysToRemove) {
                cache.remove(key)
            }
        }
    }

    /**
     * Entry point for processing nodes when this Runnable is executed.
     */
    override fun run() {
        processNodes(nodes)
    }

    /**
     * Shuts down the executor service and cancels all running tasks.
     */
    fun shutdown() {
        executor.shutdown()
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow()
            }
        } catch (ex: InterruptedException) {
            executor.shutdownNow()
            Thread.currentThread().interrupt()
        }
    }
}