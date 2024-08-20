package miki.learn.finglance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDataBase : RoomDatabase() {
    abstract fun stockDao(): StockDao

    companion object {
        @Volatile
        private var Instance: StockDataBase? = null

        fun getDatabase(context: Context): StockDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, StockDataBase::class.java, "stock_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}