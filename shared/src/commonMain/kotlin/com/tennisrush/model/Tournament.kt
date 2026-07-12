package com.tennisrush.model

import kotlinx.serialization.Serializable

@Serializable
enum class TournamentCategory {
    GRAND_SLAM, ATP_1000, WTA_1000, ATP_500, WTA_500, ATP_250, WTA_250, FINALS
}

@Serializable
enum class Surface { HARD, CLAY, GRASS }

@Serializable
enum class TicketSaleStatus { UPCOMING, BALLOT, ON_SALE, SELLING_FAST, SOLD_OUT }

@Serializable
data class Tournament(
    val id: String,
    val name: String,
    val nameCn: String,
    val category: TournamentCategory,
    val surface: Surface,
    val location: String,
    val country: String,
    val countryCode: String,
    val startDate: String,     // ISO 8601
    val endDate: String,       // ISO 8601
    val imageUrl: String,
    val ticketStatus: TicketSaleStatus,
    val ticketSaleDate: String? = null,  // ISO 8601
    val officialUrl: String? = null,
    val prizeMoney: String? = null,
    val defendingChampion: String? = null,
    val description: String? = null,
    val featured: Boolean = true
)
