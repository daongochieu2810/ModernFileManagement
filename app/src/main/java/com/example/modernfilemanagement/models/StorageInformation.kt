package com.example.modernfilemanagement.models

class StorageInformation constructor(val amountUsed: Float, val totalAmount: Float, val storageType: StorageType) {
    enum class StorageType(val displayText : String) {
        INTERNAL("Internal Storage"), EXTERNAL("External Storage")
    }
}