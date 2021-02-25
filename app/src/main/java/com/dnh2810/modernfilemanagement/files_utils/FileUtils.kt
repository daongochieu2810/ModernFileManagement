package com.dnh2810.modernfilemanagement.files_utils

import java.io.File

import com.dnh2810.modernfilemanagement.models.FileModel
import com.dnh2810.modernfilemanagement.models.FileType

object FileUtils {

    fun getFilesFromPath(
        path: String,
        showHiddenFiles: Boolean = false,
        onlyFolders: Boolean = false
    ): List<File> {
        val file = File(path)
        return file.listFiles()!!
            .filter { showHiddenFiles || !it.name.startsWith(".") }
            .filter { !onlyFolders || it.isDirectory }
            .toList()
    }

    fun getFileModelsFromFiles(files: List<File>): List<FileModel> {
        return files.map {
            FileModel(it.path, FileType.getFileType(it), it.name, convertFileSizeToMb(it.length()), it.extension)
        }
    }

    private fun convertFileSizeToMb(sizeInBytes: Long): Double {
        return (sizeInBytes.toDouble()) / (1024 * 1024)
    }
}