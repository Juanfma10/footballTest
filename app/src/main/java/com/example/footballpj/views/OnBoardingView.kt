package com.example.footballpj.views

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.footballpj.models.Team
import com.example.footballpj.viewsModels.OnboardingViewModel
import kotlinx.serialization.json.Json

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel= hiltViewModel(),
    onFinish: () -> Unit
) {
    val query = remember { mutableStateOf("") }
    val teams by viewModel.teams.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val selectedTeams = remember { mutableStateListOf<Team>() }
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Selecciona tus equipos favoritos", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = query.value,
            onValueChange = {
                query.value = it
                viewModel.search(it)
            },
            label = { Text("Buscar equipos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (loading) {
            CircularProgressIndicator()
        }

        if (error != null) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(teams) { team ->
                val isSelected = selectedTeams.contains(team)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isSelected) selectedTeams.remove(team)
                            else selectedTeams.add(team)
                        }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = team.logo,
                        contentDescription = team.name,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(team.name, modifier = Modifier.weight(1f))
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = {
                            if (it) selectedTeams.add(team)
                            else selectedTeams.remove(team)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                saveSelectedTeams(selectedTeams, context)
                onFinish()
            },
            enabled = selectedTeams.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}

fun saveSelectedTeams(selectedTeams: List<Team>, context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val editor = prefs.edit()
    val json = Json.encodeToString(selectedTeams)
    editor.putString("favorite_teams", json)
    editor.apply()
}


