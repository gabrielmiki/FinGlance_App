package miki.learn.finglance.data.repository

import kotlinx.coroutines.flow.Flow
import miki.learn.finglance.data.local.UserDataEntity
import miki.learn.finglance.domain.model.UserData

interface UserDbRepositoryInterface {

    suspend fun insertUserData(
        userData: UserData
    )

    suspend fun updateUserData(
        userData: UserData
    )

    suspend fun deleteUserData(
        userData: UserData
    )

    fun getUserDataDefaultId(
        query: String
    ): Flow<UserData>

}