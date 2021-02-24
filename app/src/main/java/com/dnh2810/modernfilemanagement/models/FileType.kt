package com.dnh2810.modernfilemanagement.models

import java.io.File

enum class FileType {
    FILE,
    FOLDER;

    companion object {
        fun getFileType(fileItem: File) = when (fileItem.isDirectory) {
            true -> FOLDER
            false -> FILE
        }
    }
}