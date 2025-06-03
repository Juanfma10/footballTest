package com.example.footballpj.models.currentseason
import kotlinx.serialization.Serializable


@Serializable
data class LeagueCurrentWrapper(
    val seasons: List<CurrentSeason>
)