package com.example.modernfilemanagement.views

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modernfilemanagement.R
import com.example.modernfilemanagement.adapters.DirectoryAdapter
import com.example.modernfilemanagement.databinding.ActivityStorageBinding
import com.example.modernfilemanagement.models.Directory
import com.example.modernfilemanagement.models.StorageInformation
import com.example.modernfilemanagement.utils.StringUtil.STORAGE_INFO
import java.time.LocalDate

class StorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorageBinding
    private lateinit var storageInformation: StorageInformation

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage)
        storageInformation = intent.getParcelableExtra(STORAGE_INFO)!!

        val basicStatsText = styleBasicStatsText(storageInformation.amountUsed.toString(),
            storageInformation.totalAmount.toString())
        val numberOfItemsText = styleNumberOfItemsText(storageInformation.totalItems.toString())

        bind(basicStatsText, numberOfItemsText)
        populateDirectories()
    }

    private fun bind(basicStatsText: SpannableString, numberOfItems: SpannableString) {
        binding.apply {
            this.basicStatsText = basicStatsText
            this.numberOfItems = numberOfItems
            executePendingBindings()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateDirectories() {
        val directoryContainer = binding.directoryContainer
        val directories = listOf(Directory("OneDrive", R.drawable.onedrive, listOf(), listOf(), LocalDate.now(), 50f))
        val directoryAdapter = DirectoryAdapter(this, directories)
        val layoutManager =  LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        directoryContainer.layoutManager = layoutManager
        directoryContainer.adapter = directoryAdapter
    }

    private fun styleNumberOfItemsText(numberOfItems: String): SpannableString {

        val s = SpannableString("$numberOfItems\n Items")
        s.setSpan(ForegroundColorSpan(Color.YELLOW), 0, numberOfItems.length, 0)
        s.setSpan(RelativeSizeSpan(1.5f), 0, numberOfItems.length, 0)

        return s
    }

    private fun styleBasicStatsText(amountUsed: String, totalAmount: String) : SpannableString {

        val s = SpannableString(amountUsed + "GB / " + totalAmount + "GB")
        s.setSpan(ForegroundColorSpan(Color.WHITE), 0, s.length, 0)
        s.setSpan(RelativeSizeSpan(0.8f), amountUsed.length, amountUsed.length + 4, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), amountUsed.length + 1, amountUsed.length + 3, 0)
        s.setSpan(StyleSpan(Typeface.BOLD), 0, amountUsed.length, 0)
        s.setSpan(RelativeSizeSpan(1.2f), 0, amountUsed.length, 0)

        val checkpoint = amountUsed.length + 5
        s.setSpan(RelativeSizeSpan(0.8f), s.length - 2, s.length, 0)
        s.setSpan(StyleSpan(Typeface.BOLD), checkpoint, s.length - 2, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), s.length - 1, s.length - 1, 0)
        s.setSpan(RelativeSizeSpan(1.2f), checkpoint, s.length - 2, 0)

        return s
    }
}