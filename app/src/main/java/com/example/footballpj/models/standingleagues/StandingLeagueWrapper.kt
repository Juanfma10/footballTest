package com.example.footballpj.models.standingleagues
import kotlinx.serialization.Serializable

@Serializable
data class StandingLeagueWrapper(
    val league: LeagueWithStandings
)