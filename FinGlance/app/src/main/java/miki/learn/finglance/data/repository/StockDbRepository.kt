package miki.learn.finglance.data.repository

import miki.learn.finglance.data.local.CompanyListingEntity
import miki.learn.finglance.data.local.StockDao

class StockDbRepository(
    private val stockDao: StockDao
) : StockDbRepositoryInterface {

    override suspend fun insertCompanyListings(companyListingEntity: List<CompanyListingEntity>) {
        stockDao.insertCompanyListings(companyListingEntity)
    }

    override suspend fun clearCompanyListings() {
        stockDao.clearCompanyListings()
    }

    override suspend fun searchForCompanyListing(query: String): List<CompanyListingEntity> {
        return stockDao.searchForCompanyListing(query)
    }

}