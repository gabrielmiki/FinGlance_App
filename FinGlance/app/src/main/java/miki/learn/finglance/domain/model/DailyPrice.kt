package miki.learn.finglance.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyPrice(
    val date: LocalDate,
    val close: Double
)
