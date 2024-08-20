package miki.learn.finglance.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import miki.learn.finglance.data.local.UserDao
import miki.learn.finglance.data.local.UserDataEntity
import miki.learn.finglance.data.mapper.toUserData
import miki.learn.finglance.data.mapper.toUserDataEntity
import miki.learn.finglance.domain.model.UserData

class UserDbRepository(
    private val userDao: UserDao
) : UserDbRepositoryInterface {

    override suspend fun insertUserData(userData: UserData) {
        userDao.insertUserData(userData.toUserDataEntity())
    }

    override suspend fun updateUserData(userData: UserData) {
        userDao.updateUserData(userData.toUserDataEntity())
    }

    override suspend fun deleteUserData(userData: UserData) {
        userDao.deleteUserData(userData.toUserDataEntity())
    }

    override fun getUserDataDefaultId(query: String): Flow<UserData> {
        return userDao.getUserDataDefaultId(query).map { it.toUserData() }
    }

}