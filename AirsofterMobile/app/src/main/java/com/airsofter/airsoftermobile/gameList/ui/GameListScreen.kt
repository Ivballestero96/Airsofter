package com.airsofter.airsoftermobile.gameList.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airsofter.airsoftermobile.R
import com.airsofter.airsoftermobile.gameList.data.network.response.Game
import com.airsofter.airsoftermobile.gameList.data.network.response.GameListResponse

@Composable
fun GameListScreen(gameListViewModel: GameListViewModel){
    val snackbarHostState = remember { SnackbarHostState() }

    // Observar la lista de juegos y el estado del filtro de ubicación
    val games by gameListViewModel.games.observeAsState()
    val isLoading: Boolean by gameListViewModel.isLoading.observeAsState(false)
    val locationFilter by gameListViewModel.locationFilter.observeAsState("")

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        GameListContent(
            modifier = Modifier.padding(contentPadding),
            games = games,
            isLoading = isLoading,
            locationFilter = locationFilter,
            onRefreshClicked = { gameListViewModel.refresh() },
            onLocationFilterChange = { gameListViewModel.setFilterByProvince(it) }
        )
    }
}
@Composable
fun GameListContent(
    modifier: Modifier,
    games: GameListResponse?,
    isLoading: Boolean,
    onLocationFilterChange: (String) -> Unit,
    onRefreshClicked: () -> Unit,
    locationFilter: String,
) {
    var gamesToLoad : List<Game> = emptyList()
    if(games?.games != null){
        gamesToLoad = games.games
    }

    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            NextGame(gamesToLoad)
            LocationFilter(locationFilter, onLocationFilterChange)
            GameList(gamesToLoad, locationFilter, onRefreshClicked)
        }
    }
}



@Composable
fun GameList(
    games: List<Game>,
    locationFilter: String,
    onRefreshClicked: () -> Unit
) {
    val filteredGames = if (locationFilter.isNotBlank()) {
        games.filter { it.location.contains(locationFilter, ignoreCase = true) }
    } else {
        games
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Title(text = stringResource(id = R.string.game_list))
            IconButton(
                onClick = onRefreshClicked,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = stringResource(id = R.string.nextGame)
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredGames) { game ->
                GameItem(game = game)
            }
        }
    }
}


@Composable
fun Title(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Start
    )
}

@Composable
fun NextGame(games: List<Game>) {
    Title(text = stringResource(id = R.string.nextGame))
    val firstGame = games.firstOrNull()

    if (firstGame != null) {
        GameItem(firstGame)
    }
}

@Composable
fun GameItem(game: Game) {
    val placeholderImage: Painter = painterResource(id = R.drawable.fieldpic)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = placeholderImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)  // Aumenta el tamaño de la imagen
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 18.dp) // Ajusta el padding de inicio según sea necesario
            ) {
                Text(
                    text = game.fieldName,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Text(
                    text = "${stringResource(id = R.string.province)}: ${game.location}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Text(
                    text = "${game.gameDateTime.substringBefore("T")} ${if (game.isAM) "AM" else "PM"}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Text(
                    text = "${stringResource(id = R.string.players)}: ${game.currentPlayers}/${game.maxPlayers}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
fun LocationFilter(locationFilter: String, onLocationFilterChange: (String) -> Unit) {
    TextField(
        value = locationFilter,
        onValueChange = {
            onLocationFilterChange(it)
        },
        label = { Text(stringResource(id = R.string.filterByProvince)) },
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        singleLine = true,
        textStyle = TextStyle(color = Color.Black)
    )
}



