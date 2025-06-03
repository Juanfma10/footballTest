package com.example.footballpj.services

import com.example.footballpj.models.teamInfo.Team
import com.example.footballpj.models.teamInfo.TeamResponse
import io.ktor.client.statement.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import com.example.footballpj.models.leagueInfo.League
import com.example.footballpj.models.leagueInfo.LeagueResponse
import com.example.footballpj.models.standingleagues.StandingsResponse
import java.time.LocalDate


class APIservices @Inject constructor(
    private val client: HttpClient
) {
    suspend fun searchTeams(query: String): List<Team> {
        val response: TeamResponse = client.get("/teams") {
            url {
                parameters.append("search", query)
            }
        }.body()

        return response.response.map { it.team }
    }

    suspend fun getLeaguesForTeams(teamIds: List<Int>): List<League> {
        val leagues = mutableListOf<League>()
        for (teamId in teamIds) {
            val response: HttpResponse = client.get("/leagues") {
                parameter("team", teamId)
                parameter("current", true)
            }
            val body = response.body<LeagueResponse>()
            leagues.addAll(body.response.map { it.league })
        }
        return leagues.distinctBy { it.id }
    }

    suspend fun getStandings(leagueId: Int): StandingsResponse {
        val currentYear = LocalDate.now().year
        var response: StandingsResponse? = null

        for (year in currentYear downTo currentYear - 3) {
            val result = client.get("/standings") {
                parameter("league", leagueId)
                parameter("season", year)
            }.body<StandingsResponse>()

            if (result.response.isNotEmpty()) {
                response = result
                break
            }
        }

        return response ?: StandingsResponse(response = emptyList())
    }
}










