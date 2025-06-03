package com.example.footballpj.models

import android.content.Context
import com.example.footballpj.models.teamInfo.Team
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun setOnboardingCompleted(completed: Boolean) {
        prefs.edit().putBoolean("onboarding_completed", completed).apply()
    }

    fun isOnboardingCompleted(): Boolean {
        return prefs.getBoolean("onboarding_completed", false)
    }

    // 🔹 Guarda equipos seleccionados como Set<String>
    fun saveSelectedTeams(teams: List<Team>) {
        val json = Json.encodeToString(teams)
        prefs.edit().putString("favorite_teams", json).apply()
    }

    // 🔹 Obtiene los equipos seleccionados
    fun getSelectedTeams(): List<Team> {
        val json = prefs.getString("favorite_teams", "[]") ?: "[]"
        return try {
            Json.decodeFromString(json)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // 🔹 Borra la selección
    fun clearSelectedTeams() {
        prefs.edit().remove("selected_teams").apply()
    }
}