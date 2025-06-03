package com.example.footballpj.viewsModels

import androidx.compose.runtime.State
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballpj.models.PreferenceManager
import com.example.footballpj.models.leagueInfo.League
import com.example.footballpj.models.teamInfo.Team
import com.example.footballpj.services.APIservices
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiServices: APIservices,
    private val preferenceManager: PreferenceManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _selectedTeams = MutableStateFlow<List<Team>>(emptyList())
    val selectedTeams: StateFlow<List<Team>> = _selectedTeams

    val _leagues = mutableStateOf<List<League>>(emptyList())
    val leagues: State<List<League>> = _leagues

    val _loading = mutableStateOf(false)
    val loading:  State<Boolean> = _loading

    val _error = mutableStateOf<String?>(null)
    val error:  State<String?> = _error

    init {
        loadSelectedTeamsAndLeagues()
    }
    private fun loadSelectedTeams() {
        val savedTeams = preferenceManager.getSelectedTeams()
        _selectedTeams.value = savedTeams.toList()
    }

    private fun loadSelectedTeamsAndLeagues() {
        viewModelScope.launch {
            _loading.value = true
            try {
                // Leer equipos favoritos desde SharedPreferences
                val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val json = prefs.getString("favorite_teams", "[]")
                val teams = Json.decodeFromString<List<Team>>(json ?: "[]")

                _selectedTeams.value = teams

                val ids = teams.map { it.id }
                val result = apiServices.getLeaguesForTeams(ids)

                _leagues.value = result.distinctBy { it.id }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
