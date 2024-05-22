package com.airsofter.airsoftermobile.gameDetail.ui

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.gameDetail.data.network.model.GameDetailDto

@Composable
fun GameDetailScreen(
    gameId: String?,
    navController: NavHostController,
    gameDetailViewModel: GameDetailViewModel
) {
    LaunchedEffect(gameId) {
        if (gameId != null) {
            gameDetailViewModel.loadGameDetail(gameId)
        }
    }

    val game by gameDetailViewModel.game.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        game?.let {
            GameDetailContent(it, navController)
        } ?: run {
            // Opcionalmente, muestra un indicador de carga o un mensaje
            Text(text = "Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun GameDetailContent(game: GameDetailDto, navController: NavHostController) {
    val placeholderImage: Painter = painterResource(id = R.drawable.fieldpic)
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
                Button(
                    onClick = {
                        navController.navigate("GameListScreenKey")
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = stringResource(id = R.string.backToList))
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
                    text = "${game.provinceName.orEmpty()}, ${game.countryName.orEmpty()}",
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
                    text = "Players: ${game.currentPlayers}/${game.maxPlayers}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                Text(
                    text = "${game.players?.joinToString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
