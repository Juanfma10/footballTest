package com.example.footballpj.services

import com.example.footballpj.models.teamInfo.Team
import com.example.footballpj.models.teamInfo.TeamResponse
import io.ktor.client.statement.* // Necesario para HttpResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import com.example.footballpj.models.leagueInfo.League
import com.example.footballpj.models.leagueInfo.LeagueResponse


class APIservices @Inject constructor(
    private val client: HttpClient
) {
    suspend fun searchTeams(query: String): List<Team> {
        val response: TeamResponse = client.get("/teams") {
            url {
                parameters.append("search=", query)
            }
        }.body()


        //Log.d("API_JSON", response.response.map { it.team })  // 👈 Imprime el JSON en consola (Logcat)
        return response.response.map { it.team };
    }

    suspend fun getLeaguesForTeams(teamIds: List<Int>): List<League> {
        val leagues = mutableListOf<League>()
        for (teamId in teamIds) {
            val response: HttpResponse = client.get("/leagues") {
                parameter("team", teamId)
            }
            val body = response.body<LeagueResponse>() // asumiendo tu modelo
            leagues.addAll(body.response.map { it.league })
        }
        return leagues
    }
}










