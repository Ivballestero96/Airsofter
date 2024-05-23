package com.airsofter.airsoftermobile

import UserManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airsofter.airsoftermobile.gameDetail.ui.GameDetailViewModel
import com.airsofter.airsoftermobile.gameList.ui.GameListViewModel
import com.airsofter.airsoftermobile.home.ui.HomeScreen
import com.airsofter.airsoftermobile.login.ui.LoginScreen
import com.airsofter.airsoftermobile.login.ui.LoginViewModel
import com.airsofter.airsoftermobile.profile.ProfileScreen
import com.airsofter.airsoftermobile.register.ui.RegisterScreen
import com.airsofter.airsoftermobile.register.ui.RegisterViewModel
import com.airsofter.airsoftermobile.ui.theme.AirsofterMobileTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val gameListViewModel: GameListViewModel by viewModels()
    private  val gameDetailViewModel: GameDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirsofterMobileTheme {
                val navigationController = rememberNavController()
                val context = LocalContext.current
                UserManager.initialize(context)

                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { contentPadding ->
                    // Screen content
                    CurrentScreen(Modifier.padding(contentPadding), scope, snackbarHostState, navigationController)
                }
            }
        }
    }

    @Composable
    fun CurrentScreen(
        modifier: Modifier,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        navigationController: NavHostController
    ) {
        Surface(
            modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navigationController, startDestination = if (UserManager.getCurrentUser() != null) "HomeScreenKey" else "LoginScreenKey") {
                composable("LoginScreenKey")
                {
                    LoginScreen(
                        loginViewModel = loginViewModel,
                        navController = navigationController,
                        scope = scope,
                        snackbarHostState = snackbarHostState)
                }
                composable("RegisterScreenKey")
                {
                    RegisterScreen(
                        registerViewModel = registerViewModel,
                        navController = navigationController,
                        scope = scope,
                        snackbarHostState = snackbarHostState)
                }
                composable("HomeScreenKey")
                {
                    HomeScreen(gameListViewModel = gameListViewModel, gameDetailViewModel = gameDetailViewModel)
                }
                composable("ProfileScreenKey"){
                    ProfileScreen()
                }
            }
        }
    }

}
