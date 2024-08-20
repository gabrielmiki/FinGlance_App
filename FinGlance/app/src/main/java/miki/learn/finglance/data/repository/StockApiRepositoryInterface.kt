package miki.learn.finglance.data.repository

import kotlinx.coroutines.flow.Flow
import miki.learn.finglance.domain.model.CompanyInfo
import miki.learn.finglance.domain.model.CompanyListing
import miki.learn.finglance.domain.model.DailyPrice
import miki.learn.finglance.ui.utils.Resource

interface StockApiRepositoryInterface {

    suspend fun getListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getDailyPrice(
        symbol: String
    ): Resource<List<DailyPrice>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>

}