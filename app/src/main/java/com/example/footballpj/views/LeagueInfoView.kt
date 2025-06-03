package com.example.footballpj.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.footballpj.models.standingleagues.StandingTeam
import com.example.footballpj.viewsModels.LeagueInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeagueInfoScreen(
    navController: NavHostController,
    leagueId: Int,
    viewModel: LeagueInfoViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadStandings(leagueId)
    }

    val flatStandings by viewModel.flatStandings.collectAsState()
    val groupedStandings by viewModel.groupedStandings.collectAsState()
    val isGrouped by viewModel.isGrouped.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Tabla de Posiciones",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            when {
                loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("$error", color = MaterialTheme.colorScheme.error)
                    }
                }

                !isGrouped && flatStandings.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay datos de tabla de posiciones disponibles.", textAlign = TextAlign.Center)
                    }
                }

                isGrouped -> {
                    LazyColumn {
                        groupedStandings.forEachIndexed { index, group ->
                            item {
                                Text(
                                    text = "Grupo ${'A' + index}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                )
                            }
                            items(group) { team ->
                                TeamStandingItem(team)
                            }
                        }
                    }
                }

                else -> {
                    // Tabla plana normal
                    Text(
                        text = "Clasificación",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    LazyColumn {
                        items(flatStandings) { team ->
                            TeamStandingItem(team)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TeamStandingItem(team: StandingTeam) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            AsyncImage(
                model = team.team.logo,
                contentDescription = team.team.name,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = team.team.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${team.points} pts",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.width(60.dp)
            )
        }
    }
}