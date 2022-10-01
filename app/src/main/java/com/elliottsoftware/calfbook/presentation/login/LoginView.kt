package com.elliottsoftware.calfbook.presentation.login

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
import com.firebase.ui.auth.R

@Composable
fun LoginView(viewModel: LoginViewModel, state: LoginFormState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        BannerCard("Calf Tracker", "powered by Elliott Software")
        EmailInput()
        PasswordInput()
        LoginButton()
        LinearLoadingBar(state.showProgressBar)

    }
}
@Composable
fun LinearLoadingBar(showLoadingBar:Boolean){
    if(showLoadingBar){
        LinearProgressIndicator(modifier = Modifier
            .width(276.dp)
            .padding(start = 0.dp, 16.dp, 0.dp, 0.dp))
    }

}


@Composable
fun BannerCard(banner: String,bannerDescription:String,viewModel: LoginViewModel = viewModel()) {

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
fun EmailInput(loginViewModel: LoginViewModel = viewModel()){
    val state = loginViewModel.state

    Column(horizontalAlignment = Alignment.CenterHorizontally) {


        OutlinedTextField(value = state.email,
            onValueChange = {loginViewModel.updateEmail(it)},
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
fun PasswordInput(loginViewModel: LoginViewModel = viewModel()){
    val state = loginViewModel.state
    //todo: migrate this functionality to the viewModel?
    val icon = if(state.passwordIconChecked)
        painterResource(id = R.drawable.design_ic_visibility)
    else
        painterResource(id = R.drawable.design_ic_visibility_off)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(value = state.password,
            onValueChange = { loginViewModel.updatePassword(it) },
            placeholder = { Text(text = "Password", fontSize = 26.sp) },
            modifier = Modifier.padding(start = 0.dp, 10.dp, 0.dp, 0.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = state.passwordError != null,
            trailingIcon = {
                IconButton(onClick = {
                    loginViewModel.passwordIconChecked(!state.passwordIconChecked)
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
fun LoginButton(loginViewModel: LoginViewModel = viewModel()){
    Button(onClick = { loginViewModel.submitData()},
        modifier = Modifier
            .height(80.dp)
            .width(280.dp)
            .padding(start = 0.dp, 20.dp, 0.dp, 0.dp)) {

        Text(text = "Login",fontSize = 26.sp)
    }
}