package com.example.firebaselogin.Presentation.Signup_Screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.firebaselogin.Navigation.Screens
import com.example.firebaselogin.R
import com.example.firebaselogin.data.Constant.ServerClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.internal.ApiKey
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),

){


    val googleSignInState = viewModel.googleState.value


val launcher= rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult() ){
    val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)


    try {
        val result = account.getResult(ApiException::class.java)
        val credentials = GoogleAuthProvider.getCredential(result.idToken,null)

        viewModel.googleSignIn(credentials)
    } catch (it:ApiException){
        print(it)
    }


}










    var email by rememberSaveable {
        mutableStateOf("")
    }


    var password by rememberSaveable {
        mutableStateOf(" ")
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val state = viewModel.signupState.collectAsState(initial = null)




    Column(modifier= Modifier
        .fillMaxSize()
        .padding(start = 30.dp, end = 30.dp),
    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(modifier= Modifier.padding(bottom = 10.dp),

            text = "Create Account",
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        )



        Text(text = "enter your credientials to register",
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp, color = Color.Gray,
        )


        TextField(
            modifier= Modifier.fillMaxWidth(),
            value = email ,
             onValueChange = {
                 email = it
             },


            shape = RoundedCornerShape(8.dp),
        singleLine = true,
        placeholder = {
            Text(text = "Email")
        })

        Spacer(modifier = Modifier.height(16.dp))
        TextField(modifier= Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
            },



            shape = RoundedCornerShape(8.dp),
        singleLine = true,
        placeholder = {
            Text(text = "Password")
        })

        Button(
            onClick = {
                scope.launch {
              viewModel.RegisterUser(email, password)
                }
            },
            modifier= Modifier
                .fillMaxWidth()
                .padding(20.dp, 30.dp, 30.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(15.dp)
        ){
            Text(text = "SIgn up",
            color = Color.White,
            modifier= Modifier.padding(7.dp))


        }


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            if(state.value?.isLoading == true){
                CircularProgressIndicator()
            }




        }

        Text(modifier = Modifier
            .padding(15.dp)
            .clickable {
                navController.navigate(Screens.signInScreen.route)
            }, text = "AlreDy have an account ? SIgn In",
        fontWeight = FontWeight.Bold, color = Color.Black)


        Text(modifier= Modifier.padding(top = 40.dp),
            text = "or Connect with",
        fontWeight = FontWeight.Medium, color = Color.Gray)



        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), horizontalArrangement = Arrangement.Center) {


            IconButton(onClick = {
val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
    .requestEmail()
    .requestIdToken(ServerClient)
    .build()


                val _googleSignInClient = GoogleSignIn.getClient(context,gso)

                launcher.launch(_googleSignInClient.signInIntent)


            }) {


                Icon(modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.gmail2)
                    , contentDescription = "googleicon", tint = Color.Unspecified)

            }

            Spacer(modifier = Modifier.width(20.dp))
            IconButton(onClick = {


            }) {
                Icon(
                    modifier= Modifier.size(52.dp),
                    painter = painterResource(id = R.drawable.fb),
                    contentDescription ="facebook", tint = Color.Unspecified )




            }

        }

    }




    LaunchedEffect(key1 = state.value?.isSucess){
        scope.launch {
            if(state.value?.isSucess?.isNotEmpty()== true){
                val success = state.value?.isSucess
                Toast.makeText(context,"$success",Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = state.value?.isError){
        scope.launch {
            if(state.value?.isError?.isNotBlank()== true){
                val error = state.value?.isError
                Toast.makeText(context,"$error",Toast.LENGTH_LONG).show()
            }
        }
    }




    LaunchedEffect(key1 = googleSignInState.success ){
        scope.launch {
            if(googleSignInState.success!=null){
                Toast.makeText(context,"SIgn In SUcess",Toast.LENGTH_LONG).show()
            }
        }
    }


}