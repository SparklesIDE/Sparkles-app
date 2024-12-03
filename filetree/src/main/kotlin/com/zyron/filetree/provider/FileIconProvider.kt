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

package com.zyron.filetree.provider

import androidx.annotation.DrawableRes
import java.io.File

/**
 * Interface for providing icons for files and folders in a file tree.
 * Implementations of this interface should provide specific icons based on
 * the type of file or folder and file extensions.
 */
interface FileTreeIconProvider {

    /**
     * Returns the resource ID for the chevron icon used in the file tree.
     *
     * @return Resource ID for the chevron icon.
     */
    @DrawableRes
    fun getChevronIcon(): Int
    
    /**
     * Returns the resource ID for the default folder icon.
     *
     * @return Resource ID for the default folder icon.
     */
    @DrawableRes
    fun getDefaultFolderIcon(): Int
    
    /**
     * Returns the resource ID for the default file icon.
     *
     * @return Resource ID for the default file icon.
     */
    @DrawableRes
    fun getDefaultFileIcon(): Int    
    
    /**
     * Returns the resource ID for the folder icon based on the folder's name.
     *
     * @param folder The folder whose icon is to be determined.
     * @return Resource ID for the folder icon.
     */
    @DrawableRes
    fun getIconForFolder(folder: File): Int

    /**
     * Returns the resource ID for the file icon based on the file's name.
     *
     * @param file The file whose icon is to be determined.
     * @return Resource ID for the file icon.
     */
    @DrawableRes
    fun getIconForFile(file: File): Int

    /**
     * Returns the resource ID for the file icon based on the file's extension.
     *
     * @param extension The extension of the file whose icon is to be determined.
     * @return Resource ID for the file icon.
     */
    @DrawableRes
    fun getIconForExtension(extension: String): Int
    
}