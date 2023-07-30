package com.example.firebaselogin.Presentation.Signup_Screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaselogin.Presentation.login_Screen.GoogleSignInState
import com.example.firebaselogin.Presentation.login_Screen.SignInState
import com.example.firebaselogin.data.AuthRepository
import com.example.firebaselogin.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){


    val _signupState = Channel<SignInState>()
    val signupState = _signupState.receiveAsFlow()


    val _googleState = mutableStateOf(GoogleSignInState())

    val googleState: State<GoogleSignInState> = _googleState



    fun RegisterUser(email:String,password:String) = viewModelScope.launch {
        repository.loginUser(email, password).collect{ result ->
            when(result) {
                is Resource.Success -> {
                    _signupState.send(SignInState(isSucess = "SIgn in Suceeded"))
                }


                is Resource.Loading ->{
                    _signupState.send(SignInState(isLoading = true))
                }

                is Resource.Error -> {
                    _signupState.send(SignInState(isError = result.message))
                }
            }
        }
    }






    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        repository.googleSignIn(credential).collect{result ->
            when(result){
                is Resource.Success ->{
                    _googleState.value = GoogleSignInState(success =result.data)
                }
                is Resource.Loading -> {
                    _googleState.value = GoogleSignInState(loading = true)
                }
                is Resource.Error -> {
                    _googleState.value = GoogleSignInState(error = result.message!!)
                }
            }

        }
    }
}