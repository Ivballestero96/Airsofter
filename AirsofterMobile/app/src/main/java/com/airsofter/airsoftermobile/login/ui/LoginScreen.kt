package com.airsofter.airsoftermobile.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airsofter.airsoftermobile.R

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavHostController) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val isLoading:Boolean by loginViewModel.isLoading.observeAsState(false)

        if(isLoading){
            Box(modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)){
                CircularProgressIndicator()
            }
        }else{
            Login(Modifier.align(Alignment.Center), loginViewModel, navController)
        }
    }
}

@Composable
fun Login(modifier: Modifier, loginViewModel: LoginViewModel, navController: NavHostController) {

    val username: String by loginViewModel.username.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val loginEnable: Boolean by loginViewModel.loginEnable.observeAsState(true)

    val isLoading: Boolean by loginViewModel.isLoading.observeAsState(false)

    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(modifier = modifier) {
            HeaderLogo(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            UsernameField(username) { loginViewModel.onLoginChange(it, password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) { loginViewModel.onLoginChange(username, it) }
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable, loginViewModel, navController)
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(8.dp))
            RegisterQuestion(Modifier.align(Alignment.End))
        }
    }
}

@Composable
fun RegisterQuestion(modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.no_account),
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFB9600)
    )
}

@Composable
fun LoginButton(loginEnable : Boolean, loginViewModel: LoginViewModel, navController: NavHostController) {
    Button(
        onClick = {navController.navigate("RegisterScreenKey")}
        , modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFB9600),
            contentColor = Color.White
        ),
        enabled = loginEnable
    ) {
        Text(text = stringResource(id = R.string.login))
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = stringResource(id = R.string.password_question),
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFFB9600)
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        placeholder = { Text(text = stringResource(id = R.string.password)) },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF000000),
            unfocusedTextColor = Color(0xFF636262),
            focusedPlaceholderColor = Color(0xFF636262),
            unfocusedPlaceholderColor = Color(0xFF636262),
            focusedContainerColor = Color(0xFFDEDDDD),
            unfocusedContainerColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun UsernameField(username: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = username,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(id = R.string.username)) },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0xFF000000),
            unfocusedTextColor = Color(0xFF636262),
            focusedPlaceholderColor = Color(0xFF636262),
            unfocusedPlaceholderColor = Color(0xFF636262),
            focusedContainerColor = Color(0xFFDEDDDD),
            unfocusedContainerColor = Color(0xFFDEDDDD),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun HeaderLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo_dark),
        contentDescription = "Logo",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    )
}
