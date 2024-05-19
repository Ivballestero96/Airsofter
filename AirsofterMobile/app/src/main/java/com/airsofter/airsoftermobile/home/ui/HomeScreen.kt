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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airsofter.airsoftermobile.R
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun HomeScreen(
    navController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido ${UserManager.getCurrentUser()?.username}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun GameList() {
    val games = listOf(
        Game(
            id = "1",
            name = "Partida 1",
            startTime = Date(),
            maxPlayers = 10,
            isAM = true,
            fieldId = "Field1"
        ),
        Game(
            id = "2",
            name = "Partida 2",
            startTime = Date(),
            maxPlayers = 8,
            isAM = false,
            fieldId = "Field2"
        ),
        Game(
            id = "3",
            name = "Partida 3",
            startTime = Date(),
            maxPlayers = 12,
            isAM = true,
            fieldId = "Field1"
        )
    )
    // Agrega un relleno horizontal de 16dp a cada lado de la lista
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        // Utiliza LazyColumn para mostrar la lista de partidas
        Text(text = "Game List")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            items(games) { game ->
                // Utiliza el composable GameItem para mostrar cada juego como una carta
                GameItem(game = game)
            }
        }
    }
}

//PlaceHolder Image
//TODO RENDER ISSUES WITH IMAGE PLACEHOLDER
@Composable
fun GameItem(game: Game) {
    val placeholderImage = ImageBitmap.imageResource(R.drawable.ic_launcher_foreground)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ) ,// Color de fondo de la carta, // Color de fondo de la carta
        shape = RoundedCornerShape(8.dp) // Forma de la carta con esquinas redondeadas
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen a la izquierda
            Image(
                painter = BitmapPainter(placeholderImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp) // Tamaño de la imagen
                    .aspectRatio(1f) // Relación de aspecto cuadrada
            )

            // Información a la derecha
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(text = "Name: ${game.name}")
                Text(text = "Start Time: ${formatDate(game.startTime)}") // Formatea la fecha
                Text(text = "Max Players: ${game.maxPlayers}")
            }
        }
    }
}


// Función para formatear la fecha en formato legible
private fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

data class Game(
    val id: String,
    val name: String,
    val startTime: Date,
    val maxPlayers: Int,
    val isAM: Boolean,
    val fieldId: String
)



