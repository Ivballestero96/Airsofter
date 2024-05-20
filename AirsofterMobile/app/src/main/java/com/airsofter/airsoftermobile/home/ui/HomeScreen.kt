package com.airsofter.airsoftermobile.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airsofter.airsoftermobile.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showSystemUi = true)
@Composable
fun HomeScreen(){
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        // Screen content
        HomeContent(Modifier.padding(contentPadding))
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

@Composable
fun HomeContent(modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        NextGame()
        GameList()
    }
}



@Composable
fun GameList() {
    val games = listOf(
        Game(
            id = "1",
            name = "Partida 1",
            startTime = Date(),
            maxPlayers = 10,
            currentPlayers = 8, // Número aleatorio de jugadores actuales
            isAM = true,
            fieldId = "Field1"
        ),
        Game(
            id = "2",
            name = "Partida 2",
            startTime = Date(),
            maxPlayers = 8,
            currentPlayers = 4, // Número aleatorio de jugadores actuales
            isAM = false,
            fieldId = "Field2"
        ),
        Game(
            id = "3",
            name = "Partida 3",
            startTime = Date(),
            maxPlayers = 12,
            currentPlayers = 7, // Número aleatorio de jugadores actuales
            isAM = true,
            fieldId = "Field1"
        ),
        Game(
            id = "4",
            name = "Partida 4",
            startTime = Date(),
            maxPlayers = 15,
            currentPlayers = 12, // Número aleatorio de jugadores actuales
            isAM = false,
            fieldId = "Field3"
        ),
        Game(
            id = "5",
            name = "Partida 5",
            startTime = Date(),
            maxPlayers = 6,
            currentPlayers = 5, // Número aleatorio de jugadores actuales
            isAM = true,
            fieldId = "Field2"
        ),
        Game(
            id = "6",
            name = "Partida 6",
            startTime = Date(),
            maxPlayers = 9,
            currentPlayers = 4, // Número aleatorio de jugadores actuales
            isAM = false,
            fieldId = "Field3"
        ),
        Game(
            id = "7",
            name = "Partida 7",
            startTime = Date(),
            maxPlayers = 20,
            currentPlayers = 15, // Número aleatorio de jugadores actuales
            isAM = true,
            fieldId = "Field4"
        ),
        Game(
            id = "8",
            name = "Partida 8",
            startTime = Date(),
            maxPlayers = 11,
            currentPlayers = 10, // Número aleatorio de jugadores actuales
            isAM = false,
            fieldId = "Field2"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Title(text = stringResource(id = R.string.game_list))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(games) { game ->
                GameItem(game = game)
            }
        }
    }
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        textAlign = TextAlign.Start // Cambiado a Start
    )
}

@Composable
fun NextGame(){
    val game = Game(
        id = "1",
        name = "Partida 1",
        startTime = Date(),
        maxPlayers = 10,
        isAM = true,
        currentPlayers = 4,
        fieldId = "Field1"
    )
    Title(text = stringResource(id = R.string.nextGame))
    GameItem(game)
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
                    .padding(start = 16.dp, end = 8.dp) // Ajusta el padding de inicio según sea necesario
            ) {
                Text(
                    text = "${game.name}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Text(
                    text = "${formatDate(game.startTime)}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Text(
                    text = "${game.currentPlayers}/${game.maxPlayers}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

data class Game(
    val id: String,
    val name: String,
    val startTime: Date,
    val maxPlayers: Int,
    val currentPlayers: Int,
    val isAM: Boolean,
    val fieldId: String
)
