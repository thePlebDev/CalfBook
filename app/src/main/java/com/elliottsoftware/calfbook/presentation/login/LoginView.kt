package com.elliottsoftware.calfbook.presentation.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elliottsoftware.calfbook.domain.models.Response
import com.firebase.ui.auth.R

@Composable
fun LoginView(viewModel: LoginViewModel, state: LoginFormState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        BannerCard("Calf Tracker", "powered by Elliott Software")
        EmailInput(viewModel,state)
        PasswordInput(viewModel,state)
        SignInWithFirebase(viewModel)

    }
}



@Composable
fun BannerCard(banner: String,bannerDescription:String) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(banner,fontSize = 40.sp,
            fontWeight = FontWeight.Bold,textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 0.dp,16.dp,0.dp,0.dp)
        )
        Text(bannerDescription,fontSize = 18.sp, fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,)

    }
}

//TODO: REWORK WITH BY "PROGRAMMING TO AN INTERFACE",  instead of the hardcoded LoginViewModel
@Composable
fun EmailInput(viewModel: LoginViewModel, state: LoginFormState){


    Column(horizontalAlignment = Alignment.CenterHorizontally) {


        OutlinedTextField(value = state.email,
            onValueChange = {viewModel.updateEmail(it)},
            singleLine = true,
            isError= state.emailError != null
            ,placeholder = {
                Text(text = "Email",fontSize = 26.sp)
            },
            modifier = Modifier.padding(start = 0.dp,40.dp,0.dp,0.dp)
            ,
            textStyle = TextStyle(fontSize = 26.sp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),


            )
        if(state.emailError != null){
            Text(text = state.emailError,color = MaterialTheme.colors.error, modifier = Modifier.align(
                Alignment.End))
        }

    }

}
@Composable
fun PasswordInput(viewModel: LoginViewModel, state: LoginFormState){

    //todo: migrate this functionality to the viewModel?
    val icon = if(state.passwordIconChecked)
        painterResource(id = R.drawable.design_ic_visibility)
    else
        painterResource(id = R.drawable.design_ic_visibility_off)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(value = state.password,
            onValueChange = { viewModel.updatePassword(it) },
            placeholder = { Text(text = "Password", fontSize = 26.sp) },
            modifier = Modifier.padding(start = 0.dp, 10.dp, 0.dp, 0.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = state.passwordError != null,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.passwordIconChecked(!state.passwordIconChecked)
                }) {
                    Icon(painter = icon, contentDescription = "Visibility Icon")
                }
            },
            visualTransformation = if (state.passwordIconChecked) VisualTransformation.None
            else PasswordVisualTransformation(),
            textStyle = TextStyle(fontSize = 26.sp)
        )
        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }

}


@Composable
fun SignInWithFirebase(viewModel: LoginViewModel){
    Button(onClick = { viewModel.submitData()},
        modifier = Modifier
            .height(80.dp)
            .width(280.dp)
            .padding(start = 0.dp, 20.dp, 0.dp, 0.dp)) {

        Text(text = "Login",fontSize = 26.sp)
    }
    when(val response = viewModel.signInWithFirebaseResponse){
        is Response.Loading -> LinearLoadingBar()
        is Response.Success -> {
            if(response.data){
                //THIS IS WHERE WE WOULD DO THE NAVIGATION
                Success()
            }
        }
        is Response.Failure -> {
            //should probably show a snackbar
            Fail()
            Log.d("Login Error",response.e.message.toString())
        }
    }
}
@Composable
fun LinearLoadingBar(){
        LinearProgressIndicator(modifier = Modifier
            .width(276.dp)
            .padding(start = 0.dp, 16.dp, 0.dp, 0.dp))

}
@Composable
fun Fail() {
    Text("FAIL")
}
@Composable
fun Success() {
    Text("SUCCESS")
}