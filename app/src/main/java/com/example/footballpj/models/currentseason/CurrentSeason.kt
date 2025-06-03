package com.example.footballpj.models.currentseason
import kotlinx.serialization.Serializable

@Serializable
data class CurrentSeason(
    val year: Int,
    val current: Boolean
)