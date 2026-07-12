package com.tennisrush.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

enum class TennisTab(val label: String, val route: String) {
    HOME("赛事", "/"),
    ORDERS("订单", "/orders"),
    PROFILE("我的", "/profile")
}

@Composable
fun TennisRushApp() {
    var selectedTab by remember { mutableStateOf(TennisTab.HOME) }
    var detailTournamentId by remember { mutableStateOf<String?>(null) }

    // 由于网球抢票没有使用 Navigation Compose，使用简单状态导航
    when {
        detailTournamentId != null -> {
            TournamentDetailScreen(
                tournamentId = detailTournamentId!!,
                onBack = { detailTournamentId = null }
            )
        }
        else -> {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        TennisTab.entries.forEach { tab ->
                            NavigationBarItem(
                                selected = selectedTab == tab,
                                onClick = { selectedTab = tab },
                                icon = {
                                    Icon(
                                        when (tab) {
                                            TennisTab.HOME -> if (selectedTab == tab) Icons.Filled.SportsTennis else Icons.Outlined.SportsTennis
                                            TennisTab.ORDERS -> if (selectedTab == tab) Icons.Filled.ConfirmationNumber else Icons.Outlined.ConfirmationNumber
                                            TennisTab.PROFILE -> if (selectedTab == tab) Icons.Filled.Person else Icons.Outlined.Person
                                        },
                                        tab.label
                                    )
                                },
                                label = { Text(tab.label) }
                            )
                        }
                    }
                }
            ) { padding ->
                Box(Modifier.padding(padding)) {
                    when (selectedTab) {
                        TennisTab.HOME -> HomeScreen(onNavigateToDetail = { detailTournamentId = it })
                        TennisTab.ORDERS -> OrderScreen()
                        TennisTab.PROFILE -> ProfileScreen()
                    }
                }
            }
        }
    }
}
