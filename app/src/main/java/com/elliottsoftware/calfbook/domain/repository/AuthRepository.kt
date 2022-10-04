package com.elliottsoftware.calfbook.domain.repository

import com.elliottsoftware.calfbook.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun firebaseSignInEmailNPassword(email:String, password:String): Flow<Response<Boolean>>

    suspend fun signOut(): Flow<Response<Boolean>>

    fun getFirebaseAuthState(): Flow<Boolean>
}