package com.elliottsoftware.calfbook.domain.use_cases.auth

data class AuthUseCases(
    val loginUser: LoginUser,
    val logoutUser: LogoutUser
)