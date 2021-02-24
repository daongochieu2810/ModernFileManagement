package com.dnh2810.modernfilemanagement.views

import java.time.LocalDate

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Environment
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.dnh2810.modernfilemanagement.R
import com.dnh2810.modernfilemanagement.adapters.DirectoryAdapter
import com.dnh2810.modernfilemanagement.databinding.ActivityStorageBinding
import com.dnh2810.modernfilemanagement.models.DirectoryModel
import com.dnh2810.modernfilemanagement.models.StorageInformation
import com.dnh2810.modernfilemanagement.utils.AnimUtils
import com.dnh2810.modernfilemanagement.utils.SetUpPieChart
import com.dnh2810.modernfilemanagement.utils.StringUtils.STORAGE_INFO
import com.dnh2810.modernfilemanagement.viewmodels.StorageViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StorageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStorageBinding
    private lateinit var storageInformation: StorageInformation
    private lateinit var storageViewModel: StorageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_storage)
        storageInformation = intent.getParcelableExtra(STORAGE_INFO)!!
        storageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        if (savedInstanceState == null) {
            val filesListFragment = FilesListFragment.build {
                path = Environment.getStorageDirectory().absolutePath
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.container, filesListFragment)
                .addToBackStack(Environment.getStorageDirectory().absolutePath)
                .commit()
        }

        val basicStatsText = styleBasicStatsText(
            storageInformation.amountUsed.toString(),
            storageInformation.totalAmount.toString()
        )
        val numberOfItemsText = styleNumberOfItemsText(storageInformation.totalItems.toString())

        bind(basicStatsText, numberOfItemsText)
        setUpButtonClick()
        populateDirectories()
        setUpPieChart()
        retrieveFiles()
    }

    private fun bind(basicStatsText: SpannableString, numberOfItems: SpannableString) {
        binding.apply {
            this.basicStatsText = basicStatsText
            this.numberOfItems = numberOfItems
            executePendingBindings()
        }
    }

    private fun retrieveFiles() {

    }

    private fun setUpButtonClick() {
        val onBackButtonClick = View.OnClickListener {
            AnimUtils.scaleView(it, 1f, 1.2f)
            finish()
        }

        val onFilterButtonClick = View.OnClickListener {
            AnimUtils.scaleView(it, 1f, 1.2f)
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

    private fun populateDirectories() {
        val directoryContainer = binding.directoryContainer
        val directories = listOf(
            DirectoryModel(
                "OneDrive",
                R.drawable.onedrive,
                listOf(),
                listOf(),
                LocalDate.now(),
                50f
            )
        )
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