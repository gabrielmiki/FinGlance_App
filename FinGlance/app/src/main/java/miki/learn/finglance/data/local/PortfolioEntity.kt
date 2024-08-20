package miki.learn.finglance.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PortfolioEntity(
    val name: String,
    val symbol: String,
    var amount: Double,
    var price: Double,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
