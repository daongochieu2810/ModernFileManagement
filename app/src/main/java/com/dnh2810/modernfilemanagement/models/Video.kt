package com.dnh2810.modernfilemanagement.models

class Video(
    path: String,
    fileType: FileType,
    name: String,
    image: Int,
    size: Long,
    lastUpdate: Long,
    extension: String = "",
    val duration: Int
): FileModel(path, fileType, name, image, size, lastUpdate, extension, listOf())