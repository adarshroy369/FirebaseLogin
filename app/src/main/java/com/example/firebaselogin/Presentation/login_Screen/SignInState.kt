package com.example.firebaselogin.Presentation.login_Screen

import com.example.firebaselogin.util.Resource

data class SignInState(
    val isLoading: Boolean = false,
    val isSucess : String? = " ",
    val isError: String? = " "
)
