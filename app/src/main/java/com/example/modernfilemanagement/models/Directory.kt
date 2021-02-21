package com.example.modernfilemanagement.models

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
class Directory constructor(
    val name: String,
    val image: Int,
    private val folders: List<Directory>,
    private val files: List<File>,
    private val lastUpdate: LocalDate,
    private val memAmount: Float): Parcelable {

        @RequiresApi(Build.VERSION_CODES.O)
        fun getLastUpdateString(): String {
            val dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            return lastUpdate.format(dateTimeFormat)
        }

        fun getNumFoldersString(): String {
            return "Folders: ${folders.size}"
        }

        fun getNumFilesString(): String {
            return "Files: ${files.size}"
        }

        fun getMemAmountString(): String {
            return "Used: ${memAmount}GB"
        }
    }