package com.kaushik.shopping.signIn

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)

