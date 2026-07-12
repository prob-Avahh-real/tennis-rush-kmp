package com.tennisrush.data

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TennisRepositoryTest {

    @Test
    fun repository_contains_tournaments() {
        val tournaments = TennisRepository.tournaments
        assertTrue(tournaments.isNotEmpty(), "Should have tournaments")
    }

    @Test
    fun getTickets_returns_items() {
        val tickets = TennisRepository.getTickets("ao-2027")
        assertTrue(tickets.isNotEmpty(), "Should have tickets for AO")
    }

    @Test
    fun basePrice_grandSlam_is_80() {
        val t = TennisRepository.tournaments.first { it.id == "ao-2027" }
        assertTrue(TennisRepository.basePrice(t) == 80)
    }
}
