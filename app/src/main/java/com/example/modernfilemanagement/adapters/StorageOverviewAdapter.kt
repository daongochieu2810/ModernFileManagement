package com.example.modernfilemanagement.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.example.modernfilemanagement.databinding.StorageOverviewCardBinding
import com.example.modernfilemanagement.models.StorageInformation
import com.example.modernfilemanagement.utils.AnimUtil
import com.example.modernfilemanagement.utils.SetUpPieChart
import com.example.modernfilemanagement.utils.StringUtil.STORAGE_INFO
import com.example.modernfilemanagement.views.StorageActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class StorageOverviewAdapter(
    val context: Context,
    private val storageInformationList: List<StorageInformation>
) : RecyclerView.Adapter<StorageOverviewAdapter.StorageOverviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageOverviewViewHolder {
        return StorageOverviewViewHolder(
            StorageOverviewCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StorageOverviewViewHolder, position: Int) {
        val storageInformation = storageInformationList[position]
        holder.bind(storageInformation)
    }

    override fun getItemCount(): Int {
        return storageInformationList.size
    }

    inner class StorageOverviewViewHolder(
        private val binding: StorageOverviewCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private fun getStorageClickListener(storageInfo: StorageInformation): View.OnClickListener {

            return View.OnClickListener {
                AnimUtil.scaleView(it!!, 1f, 1.2f)
                val intent = Intent(context, StorageActivity::class.java)
                intent.putExtra(STORAGE_INFO, storageInfo)
                context.startActivity(intent)
            }
        }
        fun bind(storageInfo: StorageInformation) {
            binding.apply {
                storageInformation = storageInfo
                clickListener = getStorageClickListener(storageInfo)
                executePendingBindings()
            }
            val pieChart = SetUpPieChart().setUp(binding.pieChart)

            val usedAmountEntry = PieEntry(storageInfo.amountUsed, "Used Amount")
            val totalAmountEntry = PieEntry(storageInfo.totalAmount, "Total Amount")
            val pieDataSet = PieDataSet(listOf(usedAmountEntry, totalAmountEntry), "")
            pieDataSet.sliceSpace = 10f
            pieDataSet.selectionShift = 5f
            pieDataSet.colors = listOf(Color.YELLOW, Color.WHITE)

            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(false)
            pieChart.data = pieData

            pieChart.centerText = generateCenterText(
                storageInfo.totalAmount.toInt().toString() +
                        "GB\n", storageInfo.amountUsed.toString() + " used"
            )
            pieChart.setCenterTextSize(20f)

            pieChart.invalidate()
        }

        private fun generateCenterText(s1: String, s2: String): SpannableString {
            val s = SpannableString(s1 + s2)
            s.setSpan(RelativeSizeSpan(1.7f), 0, s1.length - 3, 0)
            s.setSpan(StyleSpan(Typeface.BOLD), 0, s1.length, 0)
            s.setSpan(ForegroundColorSpan(Color.WHITE), 0, s.length, 0)

            s.setSpan(RelativeSizeSpan(1f), s1.length, s.length - 5, 0)
            s.setSpan(StyleSpan(Typeface.BOLD), s1.length, s.length - 5, 0)

            s.setSpan(RelativeSizeSpan(0.8f), s.length - 5, s.length, 0)
            s.setSpan(StyleSpan(Typeface.NORMAL), s.length - 5, s.length, 0)
            return s
        }
    }
}