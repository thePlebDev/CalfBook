package com.elliottsoftware.calfbook.domain.repository

import com.elliottsoftware.calfbook.domain.models.Response2
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun firebaseSignInEmailNPassword(email:String, password:String): Flow<Response2<Boolean>>

}