package com.example.modernfilemanagement.utils

import android.graphics.Color
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend

class SetUpPieChart {
    fun setUp(pieChart: PieChart): PieChart {

        pieChart.setUsePercentValues(true)
        pieChart.setExtraOffsets(5f,10f,5f,5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.RED)
        pieChart.setDrawEntryLabels(false)

        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        pieChart.holeRadius = 75f
        pieChart.transparentCircleRadius = 61f
        pieChart.setDrawCenterText(true)
        pieChart.description.isEnabled = false
        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.form = Legend.LegendForm.CIRCLE
        pieChart.legend.formSize = 14f
        pieChart.legend.textSize = 14f
        pieChart.legend.yOffset = 10f
        pieChart.legend.textColor = Color.WHITE
        pieChart.rotationAngle = 0f
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true

        pieChart.setEntryLabelTextSize(12f)

        return pieChart
    }
}