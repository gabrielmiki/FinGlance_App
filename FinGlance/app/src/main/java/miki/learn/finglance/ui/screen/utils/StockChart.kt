package miki.learn.finglance.ui.screen.utils

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import miki.learn.finglance.domain.model.DailyPrice
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun StockChart(
    infos: List<DailyPrice> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = MaterialTheme.colorScheme.onBackground
) {
    val spacing = 100F
    val transparentGraphColor = remember {
        graphColor.copy(alpha = .5F)
    }

    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.close }?.plus(1))?.roundToInt() ?: 0
    }
    val lowerValue = remember(infos) {
        infos.minOfOrNull { it.close }?.toInt() ?: 0
    }

    val density = LocalDensity.current
    val textPaint = remember {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerDay = (size.width - spacing) / infos.size
        (0 until infos.size - 1 step 20).forEach {i ->
            val info = infos[i]
            val dayMonth = "${info.date.dayOfMonth}/${info.date.month}"
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    dayMonth,
                    spacing + i * spacePerDay,
                    size.height - 5,
                    textPaint
                )
            }
        }
        val priceStep = (upperValue - lowerValue) / 5F
        (0..4).forEach {i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep + i).toString(),
                    30F,
                    size.height - spacing - i * size.height / 5F,
                    textPaint
                )
            }
        }

        var lastX = 0F
        val strokePath = Path().apply {
            val height = size.height
            for(i in infos.indices) {
                val info = infos[i]
                val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerDay
                val y1 = height - spacing - (leftRatio * height).toFloat()

                val x2 = spacing + (i + 1) * spacePerDay
                val y2 = height - spacing - (rightRatio * height).toFloat()

                if(i == 0) {
                    moveTo(x1, y1)
                }

                lastX = (x1 + x2) / 2F

                quadraticBezierTo(
                    x1, y1, lastX, (y1 + y2) / 2F
                )
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}