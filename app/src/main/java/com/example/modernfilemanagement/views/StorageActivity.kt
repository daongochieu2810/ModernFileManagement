package com.example.modernfilemanagement.views

import java.time.LocalDate

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
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.modernfilemanagement.R
import com.example.modernfilemanagement.adapters.DirectoryAdapter
import com.example.modernfilemanagement.databinding.ActivityStorageBinding
import com.example.modernfilemanagement.models.Directory
import com.example.modernfilemanagement.models.StorageInformation
import com.example.modernfilemanagement.utils.AnimUtil
import com.example.modernfilemanagement.utils.SetUpPieChart
import com.example.modernfilemanagement.utils.StringUtil.STORAGE_INFO
import com.example.modernfilemanagement.viewmodels.StorageViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorageBinding
    private lateinit var storageInformation: StorageInformation
    private lateinit var storageViewModel: StorageViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage)
        storageInformation = intent.getParcelableExtra(STORAGE_INFO)!!
        storageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        val basicStatsText = styleBasicStatsText(storageInformation.amountUsed.toString(),
            storageInformation.totalAmount.toString())
        val numberOfItemsText = styleNumberOfItemsText(storageInformation.totalItems.toString())

        bind(basicStatsText, numberOfItemsText)
        setUpButtonClick()
        populateDirectories()
        setUpPieChart()
        retrieveFiles()
        for (video in storageViewModel.videos) {
            Log.i("StorageActivity", video.uri.toString())
        }
    }

    private fun bind(basicStatsText: SpannableString, numberOfItems: SpannableString) {
        binding.apply {
            this.basicStatsText = basicStatsText
            this.numberOfItems = numberOfItems
            executePendingBindings()
        }
    }

    private fun retrieveFiles() {
        storageViewModel.retrieveVideos()
    }

    private fun setUpButtonClick() {
        val onBackButtonClick = View.OnClickListener {
            AnimUtil.scaleView(it, 1f, 1.2f)
            finish()
        }

        val onFilterButtonClick = View.OnClickListener {
            AnimUtil.scaleView(it, 1f, 1.2f)
            Toast.makeText(this@StorageActivity, "Filter clicked!", Toast.LENGTH_LONG).show()
        }

        binding.apply {
            onBackClick = onBackButtonClick
            onFilterClick = onFilterButtonClick
            executePendingBindings()
        }
    }

    private fun setUpPieChart() {
        val pieChart = SetUpPieChart().setUpSmall(binding.overviewPiechart)

        val usedAmountEntry = PieEntry(storageInformation.amountUsed, "Used Amount")
        val totalAmountEntry = PieEntry(storageInformation.totalAmount, "Total Amount")
        val pieDataSet = PieDataSet(listOf(usedAmountEntry, totalAmountEntry), "")

        pieDataSet.sliceSpace = 2f
        pieDataSet.selectionShift = 2f
        pieDataSet.colors = listOf(Color.YELLOW, Color.WHITE)

        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(false)
        pieChart.data = pieData

        pieChart.invalidate()
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