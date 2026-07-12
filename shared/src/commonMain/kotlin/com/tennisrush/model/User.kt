package com.tennisrush.model

import kotlinx.serialization.Serializable

@Serializable
data class AppUser(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val phoneNumber: String? = null,
    val avatarUrl: String? = null,
    val defaultPaymentMethod: String? = null,
    val savedCards: List<SavedCard> = emptyList(),
    val createdAt: String = "",  // ISO 8601
    val notificationEnabled: Boolean = true
)

@Serializable
data class SavedCard(
    val id: String,
    val lastFour: String,
    val brand: String,
    val expMonth: Int,
    val expYear: Int,
    val isDefault: Boolean = false
)
