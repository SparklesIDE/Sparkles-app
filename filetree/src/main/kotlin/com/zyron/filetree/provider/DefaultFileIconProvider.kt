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
package com.zyron.filetree.provider

import com.zyron.filetree.R
import java.io.File

/**
 * Default implementation of the [FileTreeIconProvider] interface. Provides default icons for
 * chevrons, folders, and files, as well as custom icons based on folder names and file extensions.
 */
class DefaultFileIconProvider : FileTreeIconProvider {

  /**
   * Returns the resource ID for the chevron icon used in the file tree.
   *
   * @return Resource ID for the chevron icon.
   */
  override fun getChevronIcon(): Int {
    return R.drawable.ic_chevron
  }

  /**
   * Returns the resource ID for the default folder icon.
   *
   * @return Resource ID for the default folder icon.
   */
  override fun getDefaultFolderIcon(): Int {
    return R.drawable.ic_folder
  }

  /**
   * Returns the resource ID for the default file icon.
   *
   * @return Resource ID for the default file icon.
   */
  override fun getDefaultFileIcon(): Int {
    return R.drawable.ic_file
  }

  /**
   * Returns the resource ID for the folder icon based on the folder name.
   *
   * @param folder The folder whose icon is to be determined.
   * @return Resource ID for the folder icon.
   */
  override fun getIconForFolder(folder: File): Int {
    return when (folder.name) {
      "app" -> R.drawable.ic_folder
      "src" -> R.drawable.ic_folder
      "kotlin" -> R.drawable.ic_folder
      "java" -> R.drawable.ic_folder
      "res" -> R.drawable.ic_folder
      else -> getDefaultFolderIcon()
    }
  }

  /**
   * Returns the resource ID for the file icon based on the file name.
   *
   * @param file The file whose icon is to be determined.
   * @return Resource ID for the file icon.
   */
  override fun getIconForFile(file: File): Int {
    return when (file.name) {
      "gradlew.bat" -> R.drawable.ic_file
      "gradlew" -> R.drawable.ic_file
      "settings.gradle" -> R.drawable.ic_file
      "build.gradle" -> R.drawable.ic_file
      "gradle.properties" -> R.drawable.ic_file
      else -> getIconForExtension(file.extension)
    }
  }

  /**
   * Returns the resource ID for the file icon based on the file extension.
   *
   * @param extension The extension of the file whose icon is to be determined.
   * @return Resource ID for the file icon.
   */
  override fun getIconForExtension(extension: String): Int {
    return when (extension) {
      "xml" -> R.drawable.ic_file
      "java" -> R.drawable.ic_file
      "kt" -> R.drawable.ic_file
      else -> getDefaultFileIcon()
    }
  }
}
