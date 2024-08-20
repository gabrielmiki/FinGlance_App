package miki.learn.finglance.data.mapper

import miki.learn.finglance.data.remote.dto.DailyPriceDTO
import miki.learn.finglance.domain.model.DailyPrice
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun DailyPriceDTO.toDailyPrice(): DailyPrice {
    val pattern = "yyyy-MM-dd"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDate.parse(timeStamp, formatter)
    return DailyPrice(
        date = localDateTime,
        close = close
    )
}