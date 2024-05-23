package com.airsofter.airsoftermobile.gameDetail.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.core.model.GameDetailDto
import kotlinx.coroutines.CoroutineScope

@Composable
fun GameDetailScreen(
    gameId: String?,
    navController: NavHostController,
    gameDetailViewModel: GameDetailViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(gameId) {
        if (gameId != null) {
            gameDetailViewModel.loadGameDetail(gameId)
        }
    }

    val game by gameDetailViewModel.game.observeAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        game?.let {
            GameDetailContent(it, navController, gameDetailViewModel, context, scope, snackbarHostState)
        } ?: run {
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun GameDetailContent(
    game: GameDetailDto,
    navController: NavHostController,
    gameDetailViewModel: GameDetailViewModel,
    context: Context,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val placeholderImage: Painter = painterResource(id = R.drawable.fieldpic)
    val currentUser = UserManager.getCurrentUser()
    val isUserInGame = currentUser?.id in game.playerIds.orEmpty()
    val isGameFull = game.currentPlayers == game.maxPlayers

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp)
    ) {
        LazyColumn {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Button(
                        onClick = {
                            navController.navigate("GameListScreenKey")
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = stringResource(id = R.string.backToList))
                    }
                    Button(
                        onClick = {
                            if (!isGameFull) {
                                if (isUserInGame) {
                                    gameDetailViewModel.onCancelSignUpForGame(context, scope, snackbarHostState, navController)
                                } else {
                                    gameDetailViewModel.signUpForGame(context, scope, snackbarHostState, navController)
                                }
                            }
                        },
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isGameFull) {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                            } else if (isUserInGame) {
                                MaterialTheme.colorScheme.error
                            } else {
                                Color(0xFFFB9600)
                            }
                        ),
                        enabled = !isGameFull

                    ) {
                        Text(
                            text = if (isGameFull) {
                                stringResource(id = R.string.gameFull)
                            } else if (isUserInGame) {
                                stringResource(id = R.string.leaveGame)
                            } else {
                                stringResource(id = R.string.joinGame)
                            }
                        )
                    }

                }
            }
            item {
                Image(
                    painter = placeholderImage,
                    contentDescription = "Placeholder",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9)
                        .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .padding(horizontal = 16.dp)
                )
            }
            item {
                Text(
                    text = game.fieldName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                Text(
                    text = game.companyName.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                Text(
                    text = "${game.provinceName}, ${game.countryName.orEmpty()}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                Text(
                    text = game.description.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                Text(
                    text = "${game.gameDateTime.substringBefore("T")} ${if (game.isAM) "AM" else "PM"}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                Text(
                    text = "${stringResource(id = R.string.players)} : ${game.currentPlayers}/${game.maxPlayers}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                Text(
                    text = "${game.players?.joinToString(
                        separator = "-", prefix = stringResource(id = R.string.playersList)
                    )}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
