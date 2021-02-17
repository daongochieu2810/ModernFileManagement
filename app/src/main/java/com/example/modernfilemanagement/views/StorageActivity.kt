package com.example.modernfilemanagement.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.modernfilemanagement.R
import com.example.modernfilemanagement.databinding.ActivityStorageBinding

class StorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage)
    }
}