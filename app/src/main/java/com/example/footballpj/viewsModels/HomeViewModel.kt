package com.example.footballpj.viewsModels

import androidx.compose.runtime.State
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballpj.models.leagueInfo.League
import com.example.footballpj.services.APIservices
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchService: APIservices,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val _leagues = mutableStateOf<List<League>>(emptyList())
    val leagues: State<List<League>> = _leagues

    val _loading = mutableStateOf(false)
    val loading:  State<Boolean> = _loading

    val _error = mutableStateOf<String?>(null)
    val error:  State<String?> = _error

    init {
        loadLeagues()
    }

    private fun loadLeagues() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val json = prefs.getString("favorite_teams", "[]")
                val ids = Json.decodeFromString<List<Int>>(json ?: "[]")

                val result = searchService.getLeaguesForTeams(ids)
                _leagues.value = result.distinctBy { it.id }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
