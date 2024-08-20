package miki.learn.finglance.data.repository

import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import miki.learn.finglance.domain.model.UserData

class RealtimeStorageRepository {

    val firebaseRealTimeDataBase = FirebaseDatabase.getInstance(
        "https://finglance-964cb-default-rtdb.europe-west1.firebasedatabase.app"
    )
    val realTimeDataBaseRef = firebaseRealTimeDataBase.reference

    fun createFirebaseUserData(userData: UserData?) {
        // TODO("Add password to User Data model")

        if (userData != null) {
            realTimeDataBaseRef.child("Users")
                .child(userData.userName!!.replace(".", ""))
                .setValue(userData)
        }
    }

    suspend fun getFirebaseUserData(userData: UserData?): UserData {

        lateinit var dataSnapshot: DataSnapshot
        var userName: String = ""

//        if (userData != null) {
//
//        }

        dataSnapshot = realTimeDataBaseRef.child("Users")
            .child(userData!!.userName!!.replace(".", ""))
            .get()
            .addOnSuccessListener{}.await()

         userName = dataSnapshot.child("userName").getValue(String::class.java)!!

        return UserData(userName = userName, profilePictureUrl = "")
    }

    fun updateFirebaseUserData(userProfile: UserData?, newUserData: UserData?) {
        if (userProfile != null) {
            realTimeDataBaseRef.child("Users")
                .child(userProfile.userName!!.replace(".", ""))
                .setValue(newUserData)
        }
    }

}