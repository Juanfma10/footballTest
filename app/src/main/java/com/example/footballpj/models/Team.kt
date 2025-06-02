package com.example.footballpj.models

import kotlinx.serialization.Serializable


@Serializable
data class Team(
    val id: Int,
    val name: String,
    val logo: String
)