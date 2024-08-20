package miki.learn.finglance.ui.screen.utils

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.times
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import miki.learn.finglance.domain.model.PortfolioStock
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class PieChartData(
    val label: String,
    val value: Int,
    val color: Color
)

data class ConvexStyle(
    val blur: Dp = 5.dp,
    val offset: Dp = 4.dp,
    val glareColor: Color = Color.White.copy(0.48f),
    val shadowColor: Color = Color.Black.copy(0.48f)
)

fun randomColor(alpha:Int=255) = Color(
    Random.nextInt(256),
    Random.nextInt(256),
    Random.nextInt(256),
    alpha = alpha)

@Composable
fun ConvexPieChart(
    modifier: Modifier,
    data: List<PortfolioStock>,
    startAngle: Float = -90f,
    rotationsCount: Int = 4,
    pieSliceStyle: ConvexStyle = ConvexStyle(),
    animationSpec: AnimationSpec<Float> = tween(1_000, easing = LinearOutSlowInEasing)
) {
    val totalValuesSum = remember(data) { data.sumOf(PortfolioStock::amount) }

    val pieChartScale = remember { Animatable(0f) }
    val pieChartRotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            pieChartScale.animateTo(1f, animationSpec)
        }
        launch {
            pieChartRotation.animateTo(360f * rotationsCount, animationSpec)
        }
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier
            .aspectRatio(1f)
            .scale(pieChartScale.value)
            .rotate(pieChartRotation.value)
    ) {
        var lastValue = startAngle
        var counter = 0
        if(data.isNotEmpty()){
            data.forEach { chartData ->
                val pieSweepAngle = 360F * (chartData.amount.toFloat() / totalValuesSum.toFloat())
                drawConvexArc(
                    color = chartData.color,
                    startAngle = lastValue,
                    sweepAngle = pieSweepAngle,
                    style = pieSliceStyle,
                    useCenter = true
                )

                drawText(
                    textMeasurer,
                    chartData.symbol,
                    topLeft = Offset((-size.width / 2F) + 14.dp.toPx() + 16.dp.toPx(), counter * 14.sp.toPx()),
                    style = TextStyle(fontSize = 12.sp)
                )

                drawRect(
                    color = chartData.color,
                    size = Size(12.dp.toPx(), 12.dp.toPx()),
                    topLeft = Offset((-size.width / 2F) + 16.dp.toPx(), counter * 15.sp.toPx())
                )

                lastValue += pieSweepAngle
                counter ++
            }
        } else {
            drawConvexArc(
                color = Color(0xFF757680),
                startAngle = lastValue,
                sweepAngle = 360F,
                style = pieSliceStyle,
                useCenter = true
            )
        }
    }
}

private fun DrawScope.drawConvexArc(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    useCenter: Boolean,
    style: ConvexStyle,
) = drawIntoCanvas { canvas ->
    val rect = this.size.toRect()
    val paint = Paint()
    paint.color = color

    canvas.drawArc(rect, startAngle, sweepAngle, useCenter, paint)

    fun drawShadowArc(offsetX: Float, offsetY: Float, shadowColor: Color) {
        val shadowPaint = Paint()
        shadowPaint.color = shadowColor

        canvas.saveLayer(rect, shadowPaint)
        canvas.drawArc(rect, startAngle, sweepAngle, useCenter, shadowPaint)

        shadowPaint.asFrameworkPaint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            maskFilter = BlurMaskFilter(style.blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }

        shadowPaint.color = Color.Black

        canvas.translate(offsetX, offsetY)
        canvas.drawArc(rect, startAngle, sweepAngle, useCenter, shadowPaint)
        canvas.restore()
    }

    val offsetPx = style.offset.toPx()
    drawShadowArc(-offsetPx, -offsetPx, style.shadowColor)
    drawShadowArc(offsetPx, offsetPx, style.glareColor)
}

@Composable
fun PieChartPanel(
    modifier: Modifier,
    platesColor: Color = Color(0xFFD5F3FF),
    platesGap: Dp = 32.dp,
    style: ConvexStyle = ConvexStyle(
        blur = 12.dp,
        offset = 8.dp,
        glareColor = Color.White.copy(0.32f),
        shadowColor = Color.Black.copy(0.32f)
    ),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .innerShadow(CircleShape, style.glareColor, style.blur, -style.offset, -style.offset)
            .innerShadow(CircleShape, style.shadowColor, style.blur, style.offset, style.offset)
            .dropShadow(CircleShape, style.glareColor, style.blur, -style.offset, -style.offset)
            .dropShadow(CircleShape, style.shadowColor, style.blur, style.offset, style.offset)
            .background(platesColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(platesGap)
                .dropShadow(CircleShape, style.glareColor, style.blur, -style.offset, -style.offset)
                .dropShadow(CircleShape, style.shadowColor, style.blur, style.offset, style.offset)
                .background(platesColor, CircleShape),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

@Composable
fun TotalView(
    total: Double,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Int> =
        tween(1000, easing = FastOutSlowInEasing),
    screenSizeBasedStyleText: TextStyle,
    screenSizeBasedStyleAmount: TextStyle
) {
    val totalToDisplay = remember {
        Animatable(initialValue = 0, typeConverter = Int.VectorConverter)
    }

    LaunchedEffect(total) {
        totalToDisplay.animateTo(total.toInt(), animationSpec)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total value",
            style = screenSizeBasedStyleText
        )
        Text(
            text = "${totalToDisplay.value}$",
            style = screenSizeBasedStyleAmount,
            fontWeight = FontWeight.Medium,
        )
    }
}

fun Modifier.innerShadow(
    shape: Shape,
    color: Color = Color.Black,
    blur: Dp = 4.dp,
    offsetY: Dp = 2.dp,
    offsetX: Dp = 2.dp,
    spread: Dp = 0.dp
) = this.drawWithContent {

    drawContent()

    drawIntoCanvas { canvas ->

        val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
        val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

        val paint = Paint()
        paint.color = color

        canvas.saveLayer(size.toRect(), paint)
        canvas.drawOutline(shadowOutline, paint)

        paint.asFrameworkPaint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            if (blur.toPx() > 0) {
                maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
            }
        }

        paint.color = Color.Black

        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

fun Modifier.dropShadow(
    shape: Shape,
    color: Color = Color.Black.copy(0.25f),
    blur: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp
) = this.drawBehind {

    val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
    val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

    val paint = Paint()
    paint.color = color

    if (blur.toPx() > 0) {
        paint.asFrameworkPaint().apply {
            maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
    }

    drawIntoCanvas { canvas ->
        canvas.save()
        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}