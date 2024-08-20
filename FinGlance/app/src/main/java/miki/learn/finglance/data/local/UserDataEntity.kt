package miki.learn.finglance.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userName: String,
    val profilePictureUrl: String,
)
