package miki.learn.finglance.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserDataEntity::class],
    version = 1
)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: UserDataBase? = null

        fun getDatabase(context: Context): UserDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDataBase::class.java, "user_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}