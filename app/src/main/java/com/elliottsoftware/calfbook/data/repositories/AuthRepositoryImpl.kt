package com.elliottsoftware.calfbook.data.repositories

import com.elliottsoftware.calfbook.domain.models.Response2
import com.elliottsoftware.calfbook.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.flow


class AuthRepositoryImpl(private val auth: FirebaseAuth= Firebase.auth) : AuthRepository {

    override fun isUserAuthenticatedInFirebase(): Boolean {
        val isUserAuthenticatedInFirebase = auth.currentUser != null
        return isUserAuthenticatedInFirebase
    }

    override suspend fun firebaseSignInEmailNPassword(email:String, password:String) = flow {
        try{
            emit(Response2.Loading)

            auth.signInWithEmailAndPassword(email, password).await()
            emit(Response2.Success(true))

        } catch (e:Exception){
            emit(Response2.Failure(e))
        }


    }

}