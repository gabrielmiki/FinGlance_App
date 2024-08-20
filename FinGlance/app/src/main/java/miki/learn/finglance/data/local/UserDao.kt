package miki.learn.finglance.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import miki.learn.finglance.domain.model.UserData

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(
        userData: UserDataEntity
    )

    @Update
    suspend fun updateUserData(userData: UserDataEntity)

    @Delete
    suspend fun deleteUserData(userData: UserDataEntity)

    @Query(
        """
            SELECT * FROM userdataentity
            WHERE id == :query
        """
    )
    fun getUserDataDefaultId(
        query: String
    ): Flow<UserDataEntity>

}