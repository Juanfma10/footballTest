package com.example.footballpj.services

import com.example.footballpj.models.Team
import com.example.footballpj.models.TeamResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class SearchTeam @Inject constructor(
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
}










