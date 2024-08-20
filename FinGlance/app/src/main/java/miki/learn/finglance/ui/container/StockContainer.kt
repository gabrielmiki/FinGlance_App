package miki.learn.finglance.ui.container

import miki.learn.finglance.data.csv.CSVParser
import miki.learn.finglance.data.repository.AccountRepositoryInterface
import miki.learn.finglance.data.repository.PortfolioRepositoryInterface
import miki.learn.finglance.data.repository.RealtimeStorageRepository
import miki.learn.finglance.data.repository.StatisticsRepository
import miki.learn.finglance.data.repository.StockApiRepositoryInterface
import miki.learn.finglance.data.repository.StockDbRepositoryInterface
import miki.learn.finglance.data.repository.UserDbRepositoryInterface
import miki.learn.finglance.domain.model.CompanyListing
import miki.learn.finglance.domain.model.DailyPrice

interface StockContainer {

    /**
     * external Services
     */
    val accountRepository: AccountRepositoryInterface
    val realtimeStorageRepository: RealtimeStorageRepository
    val stockApiRepository: StockApiRepositoryInterface

    /**
     * local Services
     */
    val stockDbRepository: StockDbRepositoryInterface
    val userDbRepository: UserDbRepositoryInterface
    val portfolioDbRepository: PortfolioRepositoryInterface

    /**
     * parsers
     */
    val dailyPriceParser: CSVParser<DailyPrice>
    val companyListingsParser: CSVParser<CompanyListing>

    val statisticsRepository: StatisticsRepository

}