package com.dnh2810.modernfilemanagement

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
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
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = findNavController(R.id.fragmentHost)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentHost)!!
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.fragmentHost)
        navController.navigatorProvider.addNavigator(navigator)
        navController.setGraph(R.navigation.main_nav_graph)

        binding.mainBottomNavigation.setupWithNavController(navController)

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d("MainActivity", "Permissions granted")
                } else {
                    Toast.makeText(this, "Some features will be unavailable", Toast.LENGTH_SHORT).show()
                }
            }

        checkPermissions(listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_MEDIA_LOCATION
        ))

        Log.d("MainActivity", Const.getScreenHeight().toString() + "  " +
                Const.getScreenWidth().toString() + "  " + Const.getDensity().toString())
        /*Log.d("MainActivity", "root " + Environment.getRootDirectory() + "\nexternal " +
                Environment.getExternalStorageDirectory() + "\nstorage " + Environment.getStorageDirectory() +
                "\ndata " + Environment.getDataDirectory() + "\ndlcache " + Environment.getDownloadCacheDirectory()
        )*/
    }

    private fun checkPermissions(permissions: List<String>) {
        for (permission in permissions) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    permission,
                ) == PackageManager.PERMISSION_GRANTED -> {

                }
                shouldShowRequestPermissionRationale(permission) -> {
                    Toast.makeText(this, "$permission not allowed", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    requestPermissionLauncher.launch(permission)
                }
            }
        }
    }
}