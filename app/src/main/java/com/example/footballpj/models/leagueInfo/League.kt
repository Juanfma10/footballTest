package com.example.footballpj.models.leagueInfo

import kotlinx.serialization.Serializable

@Serializable
data class League (
    val id: Int,
    val name: String,
    val logo: String
)