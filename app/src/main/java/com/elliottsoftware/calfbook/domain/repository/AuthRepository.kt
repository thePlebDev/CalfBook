package com.elliottsoftware.calfbook.domain.repository

import android.app.Activity
import com.elliottsoftware.calfbook.domain.models.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun firebaseSignInEmailNPassword(email:String, password:String): Flow<Response<Boolean>>

}