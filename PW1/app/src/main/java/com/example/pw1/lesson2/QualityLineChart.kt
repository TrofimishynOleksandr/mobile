package com.example.pw1.lesson2

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun QualityLineChart(values: List<Float>, modifier: Modifier = Modifier) {
    val lineColor = MaterialTheme.colorScheme.primary.toArgb()
    val labelColor = MaterialTheme.colorScheme.onSurface.toArgb()

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false
                axisRight.isEnabled = false
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                setTouchEnabled(false)
                setNoDataText("Немає даних — виконайте розрахунок")
            }
        },
        update = { chart ->
            val entries = values.mapIndexed { index, value -> Entry(index.toFloat(), value) }
            val dataSet = LineDataSet(entries, "Індекс якості, %").apply {
                color = lineColor
                setCircleColor(lineColor)
                valueTextColor = labelColor
                lineWidth = 2f
                circleRadius = 3f
                setDrawValues(false)
            }
            chart.axisLeft.axisMinimum = 0f
            chart.axisLeft.axisMaximum = 100f
            chart.xAxis.textColor = labelColor
            chart.axisLeft.textColor = labelColor
            chart.data = LineData(dataSet)
            chart.invalidate()
        }
    )
}
