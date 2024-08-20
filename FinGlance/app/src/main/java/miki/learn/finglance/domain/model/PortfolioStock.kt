package miki.learn.finglance.domain.model

import androidx.compose.ui.graphics.Color
import kotlin.random.Random


fun randomColor(alpha:Int=255) = Color(
    Random.nextInt(256),
    Random.nextInt(256),
    Random.nextInt(256),
    alpha = alpha)

data class PortfolioStock(
    val name: String,
    val symbol: String,
    val amount: Double,
    val price: Double,
    // TODO("Find a way to have the id in order to retrieve the stock when choosing to edit it") val id: Int
    val color: Color = randomColor()
)