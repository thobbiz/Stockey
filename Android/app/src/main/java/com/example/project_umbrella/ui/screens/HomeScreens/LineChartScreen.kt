package com.example.project_umbrella.ui.screens.HomeScreens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import  co.yml.charts.common.model.Point
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine

val interval = floatArrayOf(10f, 20f)
val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")


@Composable
fun LineChart(
    color: Color
) {
    val steps = 6
    val pointsData = listOf(
        Point(0f, 30f),
        Point(0.5f, 36f),
        Point(1f, 33f),
        Point(1.5f, 37f),
        Point(2f, 39f),
        Point(2.5f, 43f),
        Point(3f, 41f),
        Point(3.5f, 47f),
        Point(4f, 53f),
    )

    val xAxisData = AxisData.Builder()
        .axisStepSize(60.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)
        .labelData{i ->
            daysOfWeek.getOrElse(i) {"$i"}
        }
        .labelAndAxisLinePadding(0.dp)
        .axisLineColor(Color.Transparent)
        .axisLabelColor(Color(0xff919191))
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(0.dp)
        .labelData { "" }
        .axisLineColor(Color.Transparent)
        .axisLabelColor(Color.Transparent)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        width = 6f,
                        color = Color(0xff0081f7),
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = Color.Transparent,
                        radius = 0.dp
                    ),
                    SelectionHighlightPoint(Color.Transparent),
                    ShadowUnderLine(
                        alpha = 0.25f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xff85d6f4),
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        backgroundColor = color,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            enableHorizontalLines = true,
            pathEffect = PathEffect.dashPathEffect( intervals = interval, 0F),
            enableVerticalLines = false,
            color = Color(0xffd9d9d9)
        )
    )

    LineChart(
        modifier = Modifier
        .fillMaxWidth()
        .height(350.dp),
        lineChartData = lineChartData
    )
}