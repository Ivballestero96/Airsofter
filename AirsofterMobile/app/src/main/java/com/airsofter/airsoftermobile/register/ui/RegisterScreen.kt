package com.airsofter.airsoftermobile.register.ui

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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.login.ui.LoginViewModel

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel, navController: NavHostController){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val isLoading:Boolean by registerViewModel.isLoading.observeAsState(false)

        if(isLoading){
            Box(modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)){
                CircularProgressIndicator()
            }
        }else{
            Register(Modifier.align(Alignment.Center), registerViewModel, navController)
        }
    }
}

@Composable
fun Register(modifier: Modifier, registerViewModel: RegisterViewModel, navController: NavHostController) {
    val username: String by registerViewModel.username.observeAsState("")
    val displayName: String by registerViewModel.displayName.observeAsState("")
    val email: String by registerViewModel.email.observeAsState("")
    val password: String by registerViewModel.password.observeAsState("")
    val confirmPassword: String by registerViewModel.confirmPassword.observeAsState("")

    val registerEnable: Boolean by registerViewModel.registerEnable.observeAsState(true)
    val isLoading: Boolean by registerViewModel.isLoading.observeAsState(false)

    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(modifier = modifier) {
            HeaderLogo(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            UsernameField(username) { registerViewModel.onRegisterChange(it, displayName, email, password, confirmPassword) }
            Spacer(modifier = Modifier.padding(8.dp))
            DisplayNameField(displayName) { registerViewModel.onRegisterChange(username, it, email, password, confirmPassword) }
            Spacer(modifier = Modifier.padding(8.dp))
            EmailField(email) { registerViewModel.onRegisterChange(username, displayName, it, password, confirmPassword) }
            Spacer(modifier = Modifier.padding(8.dp))
            PasswordField(password) { registerViewModel.onRegisterChange(username, displayName, email, it, confirmPassword) }
            Spacer(modifier = Modifier.padding(8.dp))
            ConfirmPasswordField(confirmPassword) { registerViewModel.onRegisterChange(username, displayName, email, password, it) }
            Spacer(modifier = Modifier.padding(16.dp))
            RegisterButton(registerEnable, registerViewModel)
            Spacer(modifier = Modifier.padding(8.dp))
            LoginQuestion(Modifier.align(Alignment.End), navController)
        }
    }
}

@Composable
fun LoginQuestion(modifier: Modifier, navController: NavHostController) {
    TextButton(
        onClick = {navController.navigate("LoginScreenKey")},
        content = {
            Text(
                text = stringResource(id = R.string.have_account),
                modifier = modifier.clickable { },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFB9600)
            )
        }
    )
}

@Composable
fun RegisterButton(registerEnable : Boolean, registerViewModel: RegisterViewModel) {
    Button(
        onClick = {}
        , modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFB9600),
            contentColor = Color.White
        ),
        enabled = registerEnable
    ) {
        Text(text = stringResource(id = R.string.register))
    }
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
fun ConfirmPasswordField(confirmPassword: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = confirmPassword,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        placeholder = { Text(text = stringResource(id = R.string.confirmPassword)) },
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
fun EmailField(email: String, onTextFieldChange: (String) -> Unit){
    TextField(
        value = email,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        placeholder = { Text(text = stringResource(id = R.string.email)) },
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
fun DisplayNameField(displayName: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = displayName,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(id = R.string.displayname)) },
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