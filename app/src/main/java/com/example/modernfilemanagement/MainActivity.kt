package com.example.modernfilemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.modernfilemanagement.databinding.ActivityMainBinding
import com.example.modernfilemanagement.utils.Const

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = findNavController(R.id.fragmentHost)
        binding.mainBottomNavigation.setupWithNavController(navController)
        Log.d("TEST", Const.getScreenHeight().toString() + "  " + Const.getScreenWidth().toString() + "  " + Const.getDensity().toString())
    }
}