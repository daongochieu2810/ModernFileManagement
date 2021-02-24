package com.dnh2810.modernfilemanagement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
class DirectoryModel constructor(
    val name: String,
    val image: Int,
    private val folders: List<DirectoryModel>,
    private val fileModels: List<FileModel>,
    private val lastUpdate: LocalDate,
    private val memAmount: Float): Parcelable {

        fun getLastUpdateString(): String {
            val dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            return lastUpdate.format(dateTimeFormat)
        }

        fun getNumItemsString(): String {
            return "Items: ${folders.size + fileModels.size}"
        }

        fun getMemAmountString(): String {
            return "Used: ${memAmount.toInt()}GB"
        }
    }