package miki.learn.finglance.ui.screen.utils

import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun BarChart() {

    val chartData = listOf(
        Pair("I", 90),
        Pair("II", 110),
        Pair("III", 70),
        Pair("IV", 205),
        Pair("V", 150),
        Pair("V", 150),
        Pair("V", 150),
        Pair("V", 150),
    )

    val spacingFromBottom = 10F

    val upperValue = remember { (chartData.maxOfOrNull { it.second }?.plus(1)) ?: 0 }
    val lowerValue = remember { (chartData.minOfOrNull { it.second }?.toInt() ?: 0) }

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
    ) {

        val canvasHeight = size.height
        val canvasWidth = size.width

        val spaceWidthRatio = canvasWidth / 15F

        //loop through each index by step of 1
        //data shown horizontally
//        (chartData.indices step 1).forEach { i ->
//            val text = chartData[i].first
//            drawContext.canvas.nativeCanvas.apply {
//                drawText(
//                    text,
//                    spaceWidthRatio / 2F + i * (spaceWidthRatio + spaceWidthRatio),
//                    canvasHeight,
//                    textPaint
//                )
//            }
//        }

        //Horizontal line
        drawLine(
            start = Offset(0F, canvasHeight - spacingFromBottom),
            end = Offset(canvasWidth, canvasHeight - spacingFromBottom),
            color = Color.Black,
            strokeWidth = 3f
        )

        //draw bars
        chartData.forEachIndexed { index, chartPair ->

            //draw text at top of each bar
//            drawContext.canvas.nativeCanvas.apply {
//                drawText(
//                    chartPair.second.toString(),
//                    spaceWidthRatio / 2F + index * (spaceWidthRatio + spaceWidthRatio),
//                    ((upperValue - chartPair.second.toFloat()) / upperValue * canvasHeight * .9F) + canvasHeight * .08F,
//                    textPaint
//                )
//            }

            //draw Bar for each value
            drawRoundRect(
                color = Color.Blue,
                topLeft = Offset(
                    index * (spaceWidthRatio + spaceWidthRatio),
                    ((upperValue - chartPair.second.toFloat()) / upperValue * canvasHeight * .9F) + canvasHeight * .1F
                ),
                size = Size(
                    spaceWidthRatio,
                    ((chartPair.second.toFloat() / upperValue) * canvasHeight * .9F) - spacingFromBottom
                ),
                cornerRadius = CornerRadius(20f, 20f)
            )
        }
    }
}