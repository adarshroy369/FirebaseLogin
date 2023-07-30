package com.example.firebaselogin.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaselogin.Presentation.Signup_Screen.SignUpScreen


@Composable
fun NavigationGraph(navController: NavHostController= rememberNavController()){

    NavHost(navController = navController, startDestination = Screens.signInScreen.route ){
        composable(route = Screens.signInScreen.route){
            SignUpScreen(navController)
        }
    }


}