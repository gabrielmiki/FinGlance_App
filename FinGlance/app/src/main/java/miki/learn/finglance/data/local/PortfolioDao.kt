package miki.learn.finglance.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewStock(stock: PortfolioEntity)

    @Update
    suspend fun updateStock(stock: PortfolioEntity)

    @Delete
    suspend fun deleteStock(stock: PortfolioEntity)

    @Query("""
        SELECT * FROM portfolioentity
        WHERE id == :id
    """)
    fun getStock(id: Int): Flow<PortfolioEntity>

    @Query(
        """
            SELECT * FROM portfolioentity
    """)
    fun getPortfolio(): Flow<List<PortfolioEntity>>

}