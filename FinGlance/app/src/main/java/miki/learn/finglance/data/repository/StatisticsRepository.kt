package miki.learn.finglance.data.repository

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import miki.learn.finglance.domain.model.DailyPrice
import java.time.LocalDate

class StatisticsRepository(
    val portfolioRepository: PortfolioRepositoryInterface,
    val alphaVentageRepository: StockApiRepositoryInterface
) {

    suspend fun calculateMonthlyStocksResults(): MutableList<List<Double>> {

        val portfolio = portfolioRepository.getPortfolio()

        var stocksReturns: MutableList<List<Double>> = mutableListOf()

        /**
         * collection of the portfolio flow results
         */
        portfolio.collect { stockList ->
            var stockRets: MutableList<List<DailyPrice>> = mutableListOf()
            stockList.forEach { stock -> stockRets.add(alphaVentageRepository.getDailyPrice(stock.symbol).data ?: listOf()) }
            stockRets.forEach {
                stocksReturns.add(monthlyReturn(it))
            }
        }

        return stocksReturns

    }

    fun monthlyReturn(dailyPrices: List<DailyPrice>): List<Double> {

        val monthsOfTheYear = listOf<String>(
            "january", "february", "march", "april", "may", "june",
            "july", "august", "september", "october", "november", "december"
        )

        var monthlyReturns: MutableList<Double> = mutableListOf()
        monthsOfTheYear.forEach() {month ->
            val prices = dailyPrices.filter { month == it.date.month.toString().lowercase() }
            monthlyReturns.add((prices.last().close / prices.first().close) - 1)
        }

        return monthlyReturns
    }
}