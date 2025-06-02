package com.example.footballpj.models.teamInfo

import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: Int,
    val logo: String,
    val name: String,
)