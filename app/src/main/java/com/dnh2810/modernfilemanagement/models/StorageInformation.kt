package com.dnh2810.modernfilemanagement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class StorageInformation constructor(
    val amountUsed: Float,
    val totalAmount: Float,
    val totalItems: Int,
    val storageType: StorageType): Parcelable {

    enum class StorageType(val displayText : String) {
        INTERNAL("Internal Storage"), EXTERNAL("External Storage")
    }
}