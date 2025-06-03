package com.example.footballpj.viewsModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballpj.models.standingleagues.StandingTeam
import com.example.footballpj.services.APIservices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueInfoViewModel @Inject constructor(
    private val apiServices: APIservices
) : ViewModel() {

    private val _groupedStandings = MutableStateFlow<List<List<StandingTeam>>>(emptyList())
    val groupedStandings: StateFlow<List<List<StandingTeam>>> = _groupedStandings

    private val _flatStandings = MutableStateFlow<List<StandingTeam>>(emptyList())
    val flatStandings: StateFlow<List<StandingTeam>> = _flatStandings

    private val _isGrouped = MutableStateFlow(false)
    val isGrouped: StateFlow<Boolean> = _isGrouped

    val loading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    fun loadStandings(leagueId: Int) {

        viewModelScope.launch {
            loading.value = true
            try {
                val result = apiServices.getStandings(leagueId)
                val standingsRaw = result.response
                    .firstOrNull()?.league?.standings

                if (standingsRaw.isNullOrEmpty()) {
                    _groupedStandings.value = emptyList()
                    _flatStandings.value = emptyList()
                    _isGrouped.value = false
                    error.value = "No hay datos disponibles"
                    return@launch
                }

                if (standingsRaw.size > 1) {
                    // Tabla por grupos
                    _groupedStandings.value = standingsRaw
                    _flatStandings.value = emptyList()
                    _isGrouped.value = true
                } else {
                    // Tabla única
                    _flatStandings.value = standingsRaw.first()
                    _groupedStandings.value = emptyList()
                    _isGrouped.value = false
                }


            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }
}
