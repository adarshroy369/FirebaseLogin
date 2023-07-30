package com.example.firebaselogin.Navigation

sealed class Screens(val route :String){
    object signInScreen :Screens(route = "SignIn_SCreen")
    object SignUpSCreen:Screens(route = "SIgnUp_Screen")
}
