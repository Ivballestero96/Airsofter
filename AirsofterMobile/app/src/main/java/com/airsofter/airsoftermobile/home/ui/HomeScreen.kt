package com.airsofter.airsoftermobile.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.airsofter.airsoftermobile.gameDetail.ui.GameDetailScreen
import com.airsofter.airsoftermobile.gameDetail.ui.GameDetailViewModel
import com.airsofter.airsoftermobile.gameList.ui.GameListScreen
import com.airsofter.airsoftermobile.gameList.ui.GameListViewModel
import com.airsofter.airsoftermobile.profile.ProfileScreen
import com.airsofter.airsoftermobile.settings.SettingsScreen

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    gameListViewModel: GameListViewModel,
    gameDetailViewModel: GameDetailViewModel,
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val items = listOf(
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(items.indexOfFirst { it.title == "Home" })
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFFB9600),
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.White,
                            selectedTextColor = Color.White
                        ),
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            when (item.title) {
                                "Home" -> navController.navigate("GameListScreenKey")
                                "Profile" -> navController.navigate("ProfileScreenKey")
                                "Settings" -> navController.navigate("SettingsScreenKey")
                            }
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = false,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            ) {
            NavHost(navController = navController, startDestination = "GameListScreenKey") {
                composable("ProfileScreenKey") {
                    ProfileScreen()
                }
                composable("GameListScreenKey") {
                    GameListScreen(gameListViewModel = gameListViewModel) { game ->
                        navController.navigate("GameDetailScreenKey/${game.id}")
                    }
                }
                composable("SettingsScreenKey") {
                    SettingsScreen()
                }
                composable(
                    route = "GameDetailScreenKey/{gameId}",
                    arguments = listOf(navArgument("gameId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val gameId = backStackEntry.arguments?.getString("gameId")
                    GameDetailScreen(gameId = gameId, navController = navController, gameDetailViewModel = gameDetailViewModel, scope, snackbarHostState)
                }
            }
        }
    }
}
