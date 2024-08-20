package miki.learn.finglance.ui.container

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import miki.learn.finglance.data.csv.CSVParser
import miki.learn.finglance.data.csv.CompanyListingsParser
import miki.learn.finglance.data.csv.DailyPriceParser
import miki.learn.finglance.data.local.PortfolioDataBase
import miki.learn.finglance.data.local.StockDataBase
import miki.learn.finglance.data.local.UserDataBase
import miki.learn.finglance.data.remote.AlphaVantageService
import miki.learn.finglance.data.repository.AccountRepositoryInterface
import miki.learn.finglance.data.repository.AccountRepository
import miki.learn.finglance.data.repository.AlphaVentageRepository
import miki.learn.finglance.data.repository.PortfolioRepository
import miki.learn.finglance.data.repository.PortfolioRepositoryInterface
import miki.learn.finglance.data.repository.RealtimeStorageRepository
import miki.learn.finglance.data.repository.StatisticsRepository
import miki.learn.finglance.data.repository.StockApiRepositoryInterface
import miki.learn.finglance.data.repository.StockDbRepository
import miki.learn.finglance.data.repository.StockDbRepositoryInterface
import miki.learn.finglance.data.repository.UserDbRepository
import miki.learn.finglance.data.repository.UserDbRepositoryInterface
import miki.learn.finglance.domain.model.CompanyListing
import miki.learn.finglance.domain.model.DailyPrice
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AlphaVantageContainer(
    private val context: Context,
) : StockContainer {

    private val baseUrl = "https://www.alphavantage.co"

    /**
     * external Services
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
    private val retrofitService: AlphaVantageService by lazy {
        retrofit.create(AlphaVantageService::class.java)
    }
    override val stockApiRepository: StockApiRepositoryInterface by lazy {
        AlphaVentageRepository(
            alphaVantageService = retrofitService,
            stockDbRepository = stockDbRepository,
            companyListingsParser = companyListingsParser,
            dailyPriceParser = dailyPriceParser
        )
    }
    override val accountRepository: AccountRepositoryInterface by lazy {
        AccountRepository(context = context)
    }
    override val realtimeStorageRepository: RealtimeStorageRepository by lazy {
        RealtimeStorageRepository()
    }

    /**
     * local services
     */
    override val stockDbRepository: StockDbRepositoryInterface by lazy {
        StockDbRepository(StockDataBase.getDatabase(context).stockDao())
    }
    override val userDbRepository: UserDbRepositoryInterface by lazy {
        UserDbRepository(UserDataBase.getDatabase(context).userDao())
    }
    override val portfolioDbRepository: PortfolioRepositoryInterface by lazy {
        PortfolioRepository(PortfolioDataBase.getDatabase(context).portfolioDao())
    }


    /**
     * parsers
     */
    override val dailyPriceParser: CSVParser<DailyPrice> by lazy {
        DailyPriceParser()
    }
    override val companyListingsParser: CSVParser<CompanyListing> by lazy {
        CompanyListingsParser()
    }

    override val statisticsRepository: StatisticsRepository by lazy {
        StatisticsRepository(
            portfolioRepository = portfolioDbRepository,
            alphaVentageRepository = stockApiRepository
        )
    }

}