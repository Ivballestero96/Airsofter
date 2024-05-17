package com.airsofter.airsoftermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airsofter.airsoftermobile.login.ui.LoginScreen
import com.airsofter.airsoftermobile.login.ui.LoginViewModel
import com.airsofter.airsoftermobile.register.ui.RegisterScreen
import com.airsofter.airsoftermobile.register.ui.RegisterViewModel
import com.airsofter.airsoftermobile.ui.theme.AirsofterMobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel:LoginViewModel by viewModels()
    private val registerViewModel:RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirsofterMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(navController = navigationController, startDestination = "RegisterScreenKey"){
                        composable("LoginScreenKey"){ LoginScreen(loginViewModel = loginViewModel, navigationController)}
                        composable("RegisterScreenKey"){ RegisterScreen(registerViewModel = registerViewModel, navigationController) }
                    }
                }
            }
        }
    }
}
