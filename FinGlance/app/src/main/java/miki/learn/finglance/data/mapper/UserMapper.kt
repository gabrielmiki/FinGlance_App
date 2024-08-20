package miki.learn.finglance.data.mapper

import miki.learn.finglance.data.local.UserDataEntity
import miki.learn.finglance.domain.model.UserData

fun UserDataEntity.toUserData(): UserData {
    return UserData(
        userId = id,
        userName = userName,
        profilePictureUrl = profilePictureUrl
    )
}

fun UserData.toUserDataEntity(): UserDataEntity {
    return UserDataEntity(
        userName = userName ?: "",
        profilePictureUrl = profilePictureUrl ?: ""
    )
}