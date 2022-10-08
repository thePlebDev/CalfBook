package com.elliottsoftware.calfbook.domain.use_cases

import com.elliottsoftware.calfbook.data.repositories.AuthRepositoryImpl
import com.elliottsoftware.calfbook.domain.models.Response
import com.elliottsoftware.calfbook.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignUserInUseCase(
    private val repository: AuthRepository = AuthRepositoryImpl()
) {
    suspend operator fun invoke(email:String,
                                password:String): Flow<Response<Boolean>> = repository.firebaseSignInEmailNPassword(email,password)
}