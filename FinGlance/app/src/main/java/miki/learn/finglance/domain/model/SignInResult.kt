package miki.learn.finglance.domain.model

import miki.learn.finglance.domain.model.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)
