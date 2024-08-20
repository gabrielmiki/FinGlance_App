package miki.learn.finglance.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import miki.learn.finglance.data.csv.CSVParser
import miki.learn.finglance.data.csv.CompanyListingsParser
import miki.learn.finglance.data.csv.DailyPriceParser
import miki.learn.finglance.data.mapper.toCompanyInfo
import miki.learn.finglance.data.mapper.toCompanyListing
import miki.learn.finglance.data.mapper.toCompanyListingEntity
import miki.learn.finglance.data.remote.AlphaVantageService
import miki.learn.finglance.domain.model.CompanyInfo
import miki.learn.finglance.domain.model.CompanyListing
import miki.learn.finglance.domain.model.DailyPrice
import miki.learn.finglance.ui.utils.Resource
import retrofit2.HttpException
import java.io.IOException


class AlphaVentageRepository(
    val alphaVantageService: AlphaVantageService,
    val stockDbRepository: StockDbRepositoryInterface,
    val companyListingsParser: CSVParser<CompanyListing>,
    val dailyPriceParser: CSVParser<DailyPrice>
) : StockApiRepositoryInterface {

    override suspend fun getListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = stockDbRepository.searchForCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            // Check if we want to make an API call
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = alphaVantageService.getListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Could not load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Could not load data"))
                null
            }

            remoteListings?.let { listings ->
                stockDbRepository.clearCompanyListings()
                stockDbRepository.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = stockDbRepository
                        .searchForCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))

            }
        }
    }

    override suspend fun getDailyPrice(symbol: String): Resource<List<DailyPrice>> {
        return try {
            val response = alphaVantageService.getDailyPrice(symbol = symbol)
            val results = dailyPriceParser.parse(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Could not load daily info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Could not load daily info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val results = alphaVantageService.getCompanyInfo(symbol = symbol)
            Resource.Success(results.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Could not load daily info")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Could not load daily info")
        }
    }
}