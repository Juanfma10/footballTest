package com.example.footballpj.viewsModels

import com.example.footballpj.services.APIservices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballpj.models.PreferenceManager
import com.example.footballpj.models.teamInfo.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val apiServices: APIservices,
    private val prefs: PreferenceManager
) : ViewModel() {


    private val _isOnboardingCompleted = MutableStateFlow(prefs.isOnboardingCompleted())

    val isOnboardingCompleted: StateFlow<Boolean> = _isOnboardingCompleted.asStateFlow()


    fun setOnboardingCompleted() {
        viewModelScope.launch {
            prefs.setOnboardingCompleted(true)
            _isOnboardingCompleted.value = true
        }
    }
    fun isOnboardingCompleted(): Boolean {
        return prefs.isOnboardingCompleted()
    }

    fun clearSearchResults() {
        _teams.value = emptyList()
    }

    val _teams = MutableStateFlow<List<Team>>(emptyList())
    val teams: StateFlow<List<Team>> = _teams

    val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun search(query: String) {

        if (query.isBlank()) {
            _teams.value = emptyList()
            _error.value = null
            return
        }
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val result = apiServices.searchTeams(query)
                _teams.value = result
            } catch (e: Exception) {
                _error.value = "Error al buscar equipos: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}
