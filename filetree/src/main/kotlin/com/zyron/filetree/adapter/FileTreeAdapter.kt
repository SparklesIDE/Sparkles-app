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
package com.zyron.filetree.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zyron.filetree.FileTree
import com.zyron.filetree.FileTreeAdapterUpdateListener
import com.zyron.filetree.R
import com.zyron.filetree.callback.FileTreeDiffCallback
import com.zyron.filetree.datamodel.FileTreeNode
import com.zyron.filetree.events.FileTreeEventListener
import com.zyron.filetree.provider.DefaultFileIconProvider
import com.zyron.filetree.provider.FileTreeIconProvider
import com.zyron.filetree.viewholder.FileTreeViewHolder

/**
 * Adapter for displaying a hierarchical list of files and directories in a RecyclerView.
 *
 * @param context The context for accessing resources and inflating views.
 * @param fileTree The FileTree instance used for managing the file tree data.
 * @param fileTreeIconProvider Provider for file and folder icons.
 * @param fileTreeEventListener Listener for file and folder events (optional).
 */
class FileTreeAdapter(
  private val context: Context,
  private val fileTree: FileTree,
  private val fileTreeIconProvider: FileTreeIconProvider,
  private val fileTreeEventListener: FileTreeEventListener? = null,
) :
  ListAdapter<FileTreeNode, FileTreeViewHolder>(FileTreeDiffCallback()),
  FileTreeAdapterUpdateListener {

  @JvmOverloads
  constructor(
    context: Context,
    fileTree: FileTree,
    fileTreeEventListener: FileTreeEventListener? = null,
  ) : this(context, fileTree, DefaultFileIconProvider(), fileTreeEventListener)

  private var selectedItemPosition: Int = RecyclerView.NO_POSITION

  /** Creates a new ViewHolder for a file tree item. */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileTreeViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.filetree_view_item, parent, false)
    return FileTreeViewHolder(view)
  }

  /** Binds data to the ViewHolder for the given position. */
  override fun onBindViewHolder(holder: FileTreeViewHolder, position: Int) {
    val node = getItem(position)
    onBindItemView(holder, node, position)
  }

  /** Configures the ViewHolder for the file tree item. */
  private fun onBindItemView(holder: FileTreeViewHolder, node: FileTreeNode, position: Int) {
    setItemViewLayout(holder, node)
    setItemViewPadding(holder)
    updateItemViewState(holder, node, position)

    when {
      node.file.isDirectory -> onBindDirectory(holder, node)
      node.file.isFile -> onBindFile(holder, node)
    }
  }

  /** Sets the layout parameters for the item view based on the node's level. */
  private fun setItemViewLayout(holder: FileTreeViewHolder, node: FileTreeNode) {
    val indentationPx =
      TypedValue.applyDimension(
          TypedValue.COMPLEX_UNIT_DIP,
          (14 * node.level).toFloat(),
          context.resources.displayMetrics,
        )
        .toInt()
    val isLtr = context.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
    val isRtl = context.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams

    if (isLtr) {
      layoutParams.leftMargin = indentationPx
      layoutParams.rightMargin = 0
    } else if (isRtl) {
      layoutParams.rightMargin = indentationPx
      layoutParams.leftMargin = 0
    }

    holder.itemView.layoutParams = layoutParams
  }

  /** Sets padding for the item view and its child views. */
  private fun setItemViewPadding(holder: FileTreeViewHolder) {
    holder.itemView.setPadding(4, 8, 4, 8)
    holder.chevronIconView.setPadding(4, 0, 2, 0)
    holder.fileIconView.setPadding(2, 0, 4, 0)
    holder.fileNameView.setPadding(6, 7, 7, 6)
  }

  /** Updates the state of the item view based on whether it is selected and expanded. */
  private fun updateItemViewState(holder: FileTreeViewHolder, node: FileTreeNode, position: Int) {
    holder.itemView.setBackgroundResource(R.drawable.item_view_background)
    holder.itemView.isSelected =
      position == selectedItemPosition && node.isExpanded && node.file.isDirectory
  }

  /** Binds a directory node to the ViewHolder. */
  private fun onBindDirectory(holder: FileTreeViewHolder, node: FileTreeNode) {
    val isLtr = context.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
    val isRtl = context.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

    val targetRotation =
      when {
        node.isExpanded && isLtr -> 90f
        node.isExpanded && isRtl -> 90f
        !node.isExpanded && isLtr -> 0f
        !node.isExpanded && isRtl -> 180f
        else -> 0f
      }

    holder.chevronIconView.setImageDrawable(
      ContextCompat.getDrawable(context, fileTreeIconProvider.getChevronIcon())
    )
    holder.chevronIconView.rotation = targetRotation
    holder.chevronIconView.visibility = View.VISIBLE
    holder.fileIconView.setImageDrawable(
      ContextCompat.getDrawable(context, fileTreeIconProvider.getIconForFolder(node.file))
    )
    holder.fileNameView.text = node.file.name

    val currentRotation = holder.chevronIconView.rotation
    if (currentRotation != targetRotation) {
      val rotationAnimator =
        ObjectAnimator.ofFloat(holder.chevronIconView, "rotation", currentRotation, targetRotation)
          .apply {
            duration = 300
            interpolator = LinearInterpolator()
          }
      rotationAnimator.setDuration(300)
      rotationAnimator.start()
    }

    holder.itemView.setOnClickListener {
      val previousPosition = selectedItemPosition
      if (node.isExpanded) {
        fileTree.collapseNode(node)
      } else {
        fileTree.expandNode(node)
      }
      selectedItemPosition = holder.bindingAdapterPosition
      notifyItemChanged(selectedItemPosition)
      notifyItemChanged(previousPosition)
    }

    holder.itemView.setOnLongClickListener {
      fileTreeEventListener?.onFolderLongClick(node.file) ?: false
    }
  }

  /** Binds a file node to the ViewHolder. */
  private fun onBindFile(holder: FileTreeViewHolder, node: FileTreeNode) {
    holder.chevronIconView.setImageDrawable(
      ContextCompat.getDrawable(context, fileTreeIconProvider.getChevronIcon())
    )
    holder.chevronIconView.visibility = View.INVISIBLE
    holder.fileIconView.setImageDrawable(
      ContextCompat.getDrawable(context, fileTreeIconProvider.getIconForFile(node.file))
    )
    holder.fileNameView.text = node.file.name

    holder.itemView.setOnClickListener { fileTreeEventListener?.onFileClick(node.file) }

    holder.itemView.setOnLongClickListener {
      fileTreeEventListener?.onFileLongClick(node.file) ?: false
    }
  }

  /** Updates the RecyclerView when the file tree changes. */
  override fun onFileTreeUpdated(startPosition: Int, itemCount: Int) {
    notifyItemRangeChanged(startPosition, itemCount)
  }

  /** Submits a new list to be displayed. */
  override fun submitList(list: MutableList<FileTreeNode>?) {
    super.submitList(list?.toList())
  }
}
