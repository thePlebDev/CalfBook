package com.elliottsoftware.calfbook.domain.use_cases

import com.elliottsoftware.calfbook.data.repositories.AuthRepositoryImpl
import com.elliottsoftware.calfbook.domain.repository.AuthRepository

class IsUserAuthenticated(
    private val repository:AuthRepository = AuthRepositoryImpl()
) {
    operator fun invoke() = repository.isUserAuthenticatedInFirebase()
}