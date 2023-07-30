package com.example.firebaselogin.Presentation.login_Screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaselogin.data.AuthRepository
import com.example.firebaselogin.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){


    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()



    fun LoginUser(email:String,password:String) = viewModelScope.launch {
        repository.loginUser(email, password).collect{ result ->
            when(result) {
                is Resource.Success -> {
_signInState.send(SignInState(isSucess = "login  Suceeded"))
                }


                is Resource.Loading ->{
_signInState.send(SignInState(isLoading = true))
                }

                is Resource.Error -> {
_signInState.send(SignInState(isError = result.message))
                }
            }
        }
    }

}