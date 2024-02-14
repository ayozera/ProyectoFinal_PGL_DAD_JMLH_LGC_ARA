package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.SearchBar
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel


@Composable
fun SearchBar(navController: NavHostController, appMainViewModel: AppMainViewModel) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)
    ) {
        Searcher(onSearchSelected = {
            navController.navigate("${Routs.Game.rout}/$it")
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Searcher(onSearchSelected: (String) -> Unit) {
    val context = LocalContext.current
    val games = DataUp.loadGames(context)
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }
    var filteredSongs: List<String> by remember { mutableStateOf(games) }

    SearchBar(
        query = query,
        onQueryChange = { newQuery ->
            query = newQuery
            filteredSongs = games.filter { it.contains(newQuery, ignoreCase = true) }
        },
        onSearch = { onSearchSelected(query) },
        active = isActive,
        onActiveChange = { },
        placeholder = { Text("¿Cuál es el juego?") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search, contentDescription = "Icono para buscar"
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                query = ""
                filteredSongs = games
                onSearchSelected("")
            }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Icono para borrar lo escrito"
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredSongs) { cancion ->
                TextButton(
                    onClick = {
                        query = cancion
                        onSearchSelected(cancion)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .border(
                            width = .5.dp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.game_search),
                            contentDescription = "Icono de búsqueda",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Green
                        )
                        Text(
                            text = cancion,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}