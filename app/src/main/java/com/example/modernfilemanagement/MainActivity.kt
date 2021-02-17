package com.example.modernfilemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.os.Environment
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.modernfilemanagement.databinding.ActivityMainBinding
import com.example.modernfilemanagement.utils.Const
import com.example.modernfilemanagement.utils.KeepStateNavigator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = findNavController(R.id.fragmentHost)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentHost)!!
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.fragmentHost)
        navController.navigatorProvider.addNavigator(navigator)
        navController.setGraph(R.navigation.main_nav_graph)

        binding.mainBottomNavigation.setupWithNavController(navController)
        Log.d("MainActivity", Const.getScreenHeight().toString() + "  " +
                Const.getScreenWidth().toString() + "  " + Const.getDensity().toString())
        /*Log.d("MainActivity", "root " + Environment.getRootDirectory() + "\nexternal " +
                Environment.getExternalStorageDirectory() + "\nstorage " + Environment.getStorageDirectory() +
                "\ndata " + Environment.getDataDirectory() + "\ndlcache " + Environment.getDownloadCacheDirectory()
        )*/
    }
}