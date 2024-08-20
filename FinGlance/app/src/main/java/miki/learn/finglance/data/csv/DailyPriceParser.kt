package miki.learn.finglance.data.csv

import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import miki.learn.finglance.data.mapper.toDailyPrice
import miki.learn.finglance.data.remote.dto.DailyPriceDTO
import miki.learn.finglance.domain.model.DailyPrice
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate

class DailyPriceParser : CSVParser<DailyPrice> {
    override suspend fun parse(stream: InputStream): List<DailyPrice> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = DailyPriceDTO(timestamp, close.toDouble())
                    dto.toDailyPrice()
                }
//                .filter {
//                    it.date.dayOfMonth == LocalDate.now().minusDays(1).dayOfMonth
//                }
                .sortedBy {
                    it.date.dayOfYear
                }
                .also {
                    csvReader.close()
                }
        }
    }
}