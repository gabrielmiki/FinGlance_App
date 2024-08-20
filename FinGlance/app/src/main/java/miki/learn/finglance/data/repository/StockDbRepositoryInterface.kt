package miki.learn.finglance.data.repository

import miki.learn.finglance.data.local.CompanyListingEntity

interface StockDbRepositoryInterface {

    suspend fun insertCompanyListings(
        companyListingEntity: List<CompanyListingEntity>
    )

    suspend fun clearCompanyListings()

    suspend fun searchForCompanyListing(
        query: String
    ): List<CompanyListingEntity>

}