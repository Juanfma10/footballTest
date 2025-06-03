package com.example.footballpj.models.standingleagues

import kotlinx.serialization.Serializable

@Serializable
data class TeamInfo(
    val id: Int,
    val name: String,
    val logo: String
)
