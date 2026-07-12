package com.tennisrush.model

import kotlinx.serialization.Serializable

@Serializable
enum class TicketType { GROUND_PASS, RESERVED_SEAT, HOSPITALITY, VIP, PREMIUM }

@Serializable
enum class TicketSession { DAY, NIGHT, FULL_DAY, WEEKEND }

@Serializable
enum class OrderStatus { PENDING, CONFIRMED, CANCELLED, REFUNDED }

@Serializable
data class Ticket(
    val id: String,
    val tournamentId: String,
    val type: TicketType,
    val session: TicketSession,
    val court: String,
    val round: String,
    val date: String,          // ISO 8601
    val price: Double,
    val currency: String,
    val availableCount: Int,
    val maxPerUser: Int = 4,
    val saleStart: String? = null,
    val section: String? = null,
    val row: String? = null,
    val isHot: Boolean = false
)

@Serializable
data class Order(
    val id: String,
    val userId: String,
    val ticketId: String,
    val quantity: Int,
    val totalPrice: Double,
    val currency: String,
    val status: OrderStatus,
    val createdAt: String,     // ISO 8601
    val tournamentName: String? = null,
    val ticketType: String? = null,
    val sessionDate: String? = null,
    val court: String? = null,
    val round: String? = null
)
