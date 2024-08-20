package miki.learn.finglance.data.repository

import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import miki.learn.finglance.data.local.PortfolioEntity
import miki.learn.finglance.domain.model.PortfolioStock

interface PortfolioRepositoryInterface {

    suspend fun insertNewStock(stock: PortfolioStock)

    suspend fun updateStock(stock: PortfolioStock)

    suspend fun deleteStock(stock: PortfolioStock)

    fun getStock(id: Int): Flow<PortfolioStock?>

    fun getPortfolio(): Flow<List<PortfolioStock>>
}