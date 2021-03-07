package com.dnh2810.modernfilemanagement.models

import android.os.Parcelable
import com.dnh2810.modernfilemanagement.files_utils.FileUtils
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Parcelize
open class FileModel(
    val path: String,
    val fileType: FileType,
    val name: String,
    val image: Int,
    val size: Long,
    val lastUpdate: Long,
    val extension: String = "",
    val subFiles: List<FileModel>
): Parcelable {
    fun getLastUpdateString(): String {
        val dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val dateFromMillis = Instant.ofEpochMilli(lastUpdate)
            .atZone(ZoneId.systemDefault()).toLocalDate()
        return dateFromMillis.format(dateTimeFormat)
    }

    fun getNumItemsString(): String {
        return "Items: ${subFiles.size}"
    }

    fun getMemAmountString(): String {
        return "Used: ${FileUtils.humanReadableByteCountSI(size)}"
    }
}