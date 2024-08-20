package miki.learn.finglance.data.remote

import miki.learn.finglance.data.remote.dto.CompanyInfoDTO
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageService {

    @GET("query?function=TIME_SERIES_DAILY&datatype=csv")
    suspend fun getDailyPrice(
        @Query("apikey") apiKey: String = "OOXZWN5MDK2AA0TM",
        @Query("symbol") symbol: String
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("apikey") apiKey: String = "OOXZWN5MDK2AA0TM",
        @Query("symbol") symbol: String,
    ): CompanyInfoDTO

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = "OOXZWN5MDK2AA0TM"
    ): ResponseBody

}