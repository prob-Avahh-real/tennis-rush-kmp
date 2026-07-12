package com.tennisrush.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tennisrush.data.TennisRepository
import com.tennisrush.model.*

@Composable
fun HomeScreen(onNavigateToDetail: (String) -> Unit) {
    var activeTab by remember { mutableStateOf(0) } // 0=所有, 1=大满贯, 2=大师赛, 3=年终

    Column {
        // Hero banner
        Box(Modifier.fillMaxWidth().height(160.dp)) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1591195830540-c7015e35e86e?w=800",
                contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
            )
            Text("Tennis Rush", modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
                color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        // Category filter
        ScrollableTabRow(selectedTabIndex = activeTab, edgePadding = 16.dp, modifier = Modifier.fillMaxWidth()) {
            listOf("所有赛事", "大满贯", "大师赛", "年终").forEachIndexed { i, label ->
                Tab(selected = activeTab == i, onClick = { activeTab = i }, text = { Text(label, fontSize = 14.sp) })
            }
        }

        // Content
        val all = TennisRepository.tournaments
        val filtered = when (activeTab) {
            1 -> all.filter { it.category == TournamentCategory.GRAND_SLAM }
            2 -> all.filter { it.category in listOf(TournamentCategory.ATP_1000, TournamentCategory.WTA_1000) }
            3 -> all.filter { it.category == TournamentCategory.FINALS }
            else -> all
        }

        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filtered) { tournament ->
                TournamentCard(tournament = tournament) { onNavigateToDetail(tournament.id) }
            }
        }
    }
}

@Composable
fun TournamentCard(tournament: Tournament, onClick: () -> Unit) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), onClick = onClick) {
        Column {
            AsyncImage(model = tournament.imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().height(160.dp))
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(tournament.nameCn, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.width(8.dp))
                    Surface(shape = RoundedCornerShape(4.dp), color = categoryColor(tournament.category)) {
                        Text(categoryLabel(tournament.category), modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(tournament.name, color = Color.Gray, fontSize = 14.sp)

                Spacer(Modifier.height(12.dp))

                Row {
                    InfoChip(Icons.Outlined.CalendarToday, "${tournament.startDate.take(10)} - ${tournament.endDate.take(10)}")
                    Spacer(Modifier.width(16.dp))
                    InfoChip(Icons.Outlined.LocationOn, "${tournament.location}, ${tournament.country}")
                }
                Spacer(Modifier.height(4.dp))
                Row {
                    InfoChip(Icons.Outlined.SportsTennis, surfaceLabel(tournament.surface))
                    tournament.prizeMoney?.let { Spacer(Modifier.width(16.dp)); InfoChip(Icons.Outlined.Star, it) }
                }

                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = RoundedCornerShape(20.dp), color = statusColor(tournament.ticketStatus)) {
                        Text(statusLabel(tournament.ticketStatus), modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.weight(1f))
                    tournament.defendingChampion?.let {
                        Text("卫冕：$it", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
        Spacer(Modifier.width(4.dp))
        Text(text, color = Color.Gray, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

fun categoryLabel(cat: TournamentCategory): String = when (cat) {
    TournamentCategory.GRAND_SLAM -> "大满贯"
    TournamentCategory.ATP_1000, TournamentCategory.WTA_1000 -> "1000"
    TournamentCategory.FINALS -> "年终"
    else -> "500/250"
}
fun categoryColor(cat: TournamentCategory): Color = when (cat) {
    TournamentCategory.GRAND_SLAM -> Color(0xFF4CAF50)
    TournamentCategory.ATP_1000, TournamentCategory.WTA_1000 -> Color(0xFF2196F3)
    TournamentCategory.FINALS -> Color(0xFF9C27B0)
    else -> Color(0xFFFF9800)
}
fun surfaceLabel(s: Surface): String = when (s) { Surface.HARD -> "硬地"; Surface.CLAY -> "红土"; Surface.GRASS -> "草地" }
fun statusLabel(s: TicketSaleStatus): String = when (s) {
    TicketSaleStatus.UPCOMING -> "即将开售"; TicketSaleStatus.BALLOT -> "抽签中"; TicketSaleStatus.ON_SALE -> "售票中"; TicketSaleStatus.SELLING_FAST -> "即将售罄"; TicketSaleStatus.SOLD_OUT -> "已售罄"
}
fun statusColor(s: TicketSaleStatus): Color = when (s) {
    TicketSaleStatus.UPCOMING -> Color(0xFFFF9800); TicketSaleStatus.BALLOT -> Color(0xFF2196F3); TicketSaleStatus.ON_SALE -> Color(0xFF4CAF50); TicketSaleStatus.SELLING_FAST -> Color(0xFFF44336); TicketSaleStatus.SOLD_OUT -> Color.Gray
}
