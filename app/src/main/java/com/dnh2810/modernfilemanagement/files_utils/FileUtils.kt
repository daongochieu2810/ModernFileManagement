package com.dnh2810.modernfilemanagement.files_utils

import android.util.Log
import com.dnh2810.modernfilemanagement.R
import com.dnh2810.modernfilemanagement.models.FileModel
import com.dnh2810.modernfilemanagement.models.FileType
import java.io.File
import java.text.CharacterIterator
import java.text.StringCharacterIterator

object FileUtils {

    fun getFilesFromPath(
        path: String,
        showHiddenFiles: Boolean = false,
        onlyFolders: Boolean = false
    ): List<File> {
        val file = File(path)
        Log.d("FileUtils", file.path + " " + file.isDirectory + " " + file.listFiles())
        return file.listFiles()!!
            .filter { showHiddenFiles || !it.name.startsWith(".") }
            .filter { !onlyFolders || it.isDirectory }
            .toList()
    }

    fun getFileModelsFromFiles(files: List<File>): List<FileModel> {
        return files.map {
            FileModel(it.path, FileType.getFileType(it), it.name,
                R.drawable.file_icon, it.length(),
                it.lastModified(), it.extension, listOf())
        }
    }

    fun humanReadableByteCountSI(bytes: Long): String? {
        var bytes = bytes
        if (-1000 < bytes && bytes < 1000) {
            return "$bytes B"
        }
        val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
        while (bytes <= -999950 || bytes >= 999950) {
            bytes /= 1000
            ci.next()
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current())
    }
}