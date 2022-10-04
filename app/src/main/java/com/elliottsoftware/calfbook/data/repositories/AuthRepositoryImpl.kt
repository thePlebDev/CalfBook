package com.elliottsoftware.calfbook.data.repositories

import com.elliottsoftware.calfbook.domain.models.Response
import com.elliottsoftware.calfbook.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {

    override fun isUserAuthenticatedInFirebase(): Boolean {
        val isUserAuthenticatedInFirebase = auth.currentUser != null
        return isUserAuthenticatedInFirebase
    }

    override suspend fun firebaseSignInEmailNPassword(email:String, password:String): Flow<Response<Boolean>> {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(){task ->

            }
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getFirebaseAuthState(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}