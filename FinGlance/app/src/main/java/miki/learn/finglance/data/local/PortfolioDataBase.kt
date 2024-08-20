package miki.learn.finglance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PortfolioEntity::class],
    version = 1
)
abstract class PortfolioDataBase : RoomDatabase() {

    abstract fun portfolioDao(): PortfolioDao

    companion object {
        @Volatile
        private var Instance: PortfolioDataBase? = null

        fun getDatabase(context: Context): PortfolioDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PortfolioDataBase::class.java, "portfolio_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}