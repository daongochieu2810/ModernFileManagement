package com.dnh2810.modernfilemanagement

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

import com.dnh2810.modernfilemanagement.databinding.ActivityMainBinding
import com.dnh2810.modernfilemanagement.utils.Const
import com.dnh2810.modernfilemanagement.utils.KeepStateNavigator

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

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {

                } else {
                    Toast.makeText(this, "Some features will be unavailable", Toast.LENGTH_SHORT).show()
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) == PackageManager.PERMISSION_GRANTED -> {

            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                Toast.makeText(this, "Action not allowed", Toast.LENGTH_SHORT).show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        Log.d("MainActivity", Const.getScreenHeight().toString() + "  " +
                Const.getScreenWidth().toString() + "  " + Const.getDensity().toString())
        /*Log.d("MainActivity", "root " + Environment.getRootDirectory() + "\nexternal " +
                Environment.getExternalStorageDirectory() + "\nstorage " + Environment.getStorageDirectory() +
                "\ndata " + Environment.getDataDirectory() + "\ndlcache " + Environment.getDownloadCacheDirectory()
        )*/
    }
}