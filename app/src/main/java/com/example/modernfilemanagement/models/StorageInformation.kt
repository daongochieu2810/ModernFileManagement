package com.example.modernfilemanagement.models

class StorageInformation constructor(amountUsed: Float, storageType: StorageType) {
    enum class StorageType {
        INTERNAL, EXTERNAL
    }
}