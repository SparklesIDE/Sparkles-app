package com.sparkleside.ui.components.executorservice

import android.content.Context
import android.widget.Toast
import com.zyron.filetree.events.FileTreeEventListener
import java.io.File

class FileOperationExecutor(private val context: Context) : FileTreeEventListener {

  override fun onFileClick(file: File) {
    Toast.makeText(context, "File clicked: ${file.name}", Toast.LENGTH_SHORT).show()
  }

  override fun onFolderClick(folder: File) {
    Toast.makeText(context, "Folder clicked: ${folder.name}", Toast.LENGTH_SHORT).show()
  }

  override fun onFileLongClick(file: File): Boolean {
    Toast.makeText(context, "File long-clicked: ${file.name}", Toast.LENGTH_SHORT).show()
    return true
  }

  override fun onFolderLongClick(folder: File): Boolean {
    Toast.makeText(context, "Folder long-clicked: ${folder.name}", Toast.LENGTH_SHORT).show()
    return true
  }

  override fun onFileTreeViewUpdated(startPosition: Int, itemCount: Int) {
    print("FileTreeView has ben updated.")
  }
}
