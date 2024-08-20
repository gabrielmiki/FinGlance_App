package miki.learn.finglance.ui.utils

import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import miki.learn.finglance.R
import miki.learn.finglance.domain.model.CompanyListing
import miki.learn.finglance.ui.screen.utils.PieChartData
import miki.learn.finglance.ui.screen.utils.StatisticsData

object PreviewData {
    val companyListings = listOf(
        CompanyListing(
            name = "Tesla",
            symbol = "TSLA",
            exchange = "NASDAQ"
        ),
        CompanyListing(
            name = "Apple",
            symbol = "AAPL",
            exchange = "NYSE"
        ),
        CompanyListing(
            name = "Alphabet",
            symbol = "ALPH",
            exchange = "BATS"
        ),
        CompanyListing(
            name = "Nvidia",
            symbol = "NGNX",
            exchange = "NYSE"
        ),
        CompanyListing(
            name = "Amazon",
            symbol = "AMZN",
            exchange = "BATS"
        ),
        CompanyListing(
            name = "Netflix",
            symbol = "NTFL",
            exchange = "NASDAQ"
        ),CompanyListing(
            name = "Microsoft",
            symbol = "MCRF",
            exchange = "NYSE"
        ),CompanyListing(
            name = "Goldman Sachs",
            symbol = "AAAU",
            exchange = "NASDAQ"
        ),CompanyListing(
            name = "Alcoa Corp",
            symbol = "AA",
            exchange = "NYSE"
        ),
    )

    val statisticsData = listOf(
        StatisticsData(
            graph = R.drawable.ic_launcher_background,
            type = "Returns"
        ),
        StatisticsData(
            graph = R.drawable.ic_launcher_background,
            type = "Volatility"
        ),
        StatisticsData(
            graph = R.drawable.ic_launcher_background,
            type = "Max Drawdown"
        ),
        StatisticsData(
            graph = R.drawable.ic_launcher_background,
            type = "Min Drawdown"
       ),
    )

    val pieChartData = listOf(
            PieChartData("Item-1", 30, Color(0xFFE45C5C)),
            PieChartData("Item-2", 45, Color(0xFF8FE25C)),
            PieChartData("Item-3", 25, Color(0xFF4471E4)),
            PieChartData("Item-4", 20, Color(0xFFEECE55)),
            PieChartData("Item-5", 40, Color(0xFFBD68CB)),
        )
}