package miki.learn.finglance.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import miki.learn.finglance.data.local.PortfolioDao
import miki.learn.finglance.data.local.PortfolioEntity
import miki.learn.finglance.data.mapper.toPortfolioEntity
import miki.learn.finglance.data.mapper.toPortfolioStock
import miki.learn.finglance.domain.model.PortfolioStock

class PortfolioRepository(
    private val portfolioDao: PortfolioDao
) : PortfolioRepositoryInterface {
    override suspend fun insertNewStock(stock: PortfolioStock) {
        portfolioDao.insertNewStock(stock.toPortfolioEntity())
    }

    override suspend fun updateStock(stock: PortfolioStock) {
        portfolioDao.updateStock(stock.toPortfolioEntity())
    }

    override suspend fun deleteStock(stock: PortfolioStock) {
        portfolioDao.deleteStock(stock.toPortfolioEntity())
    }

    override fun getStock(id: Int): Flow<PortfolioStock?> {
        return portfolioDao.getStock(id).map { it.toPortfolioStock() }
    }

    override fun getPortfolio(): Flow<List<PortfolioStock>> {
        return portfolioDao.getPortfolio().map { listEntity -> listEntity.map { entity -> entity.toPortfolioStock() } }
    }
}