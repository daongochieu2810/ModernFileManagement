package com.example.modernfilemanagement.viewmodels

import android.app.Application
import android.os.Environment
import androidx.lifecycle.AndroidViewModel

class StorageViewModel(application: Application) : AndroidViewModel(application) {
    val externalStoragePath = Environment.getStorageDirectory().absolutePath
}