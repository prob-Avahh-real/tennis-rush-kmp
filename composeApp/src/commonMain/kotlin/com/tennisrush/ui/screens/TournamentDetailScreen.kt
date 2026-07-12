package com.tennisrush.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun TournamentDetailScreen(tournamentId: String, onBack: () -> Unit) {
    val tournament = TennisRepository.tournaments.firstOrNull { it.id == tournamentId }
    var quantity by remember { mutableStateOf(1) }
    val tickets = remember { TennisRepository.getTickets(tournamentId) }

    tournament?.let { t ->
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(t.nameCn) }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回") } })
            }
        ) { padding ->
            Column(Modifier.padding(padding).verticalScroll(rememberScrollState())) {
                // 封面
                AsyncImage(model = t.imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().height(250.dp))

                Column(Modifier.padding(20.dp)) {
                    Text(t.nameCn, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(t.name, color = Color.Gray, fontSize = 14.sp)

                    Spacer(Modifier.height(16.dp))

                    // 基本信息网格
                    Row(Modifier.fillMaxWidth()) {
                        DetailInfoItem(Icons.Outlined.CalendarToday, "日期", "${t.startDate.take(10)} - ${t.endDate.take(10)}")
                        Spacer(Modifier.width(16.dp))
                        DetailInfoItem(Icons.Outlined.LocationOn, "地点", "${t.location}, ${t.country}")
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth()) {
                        DetailInfoItem(Icons.Outlined.SportsTennis, "场地", surfaceLabel(t.surface))
                        Spacer(Modifier.width(16.dp))
                        DetailInfoItem(Icons.Outlined.Star, "类别", categoryLabel(t.category))
                    }
                    t.prizeMoney?.let {
                        Spacer(Modifier.height(12.dp))
                        DetailInfoItem(Icons.Outlined.Star, "总奖金", it)
                    }
                    t.defendingChampion?.let {
                        Spacer(Modifier.height(12.dp))
                        DetailInfoItem(Icons.Outlined.EmojiEvents, "卫冕冠军", it)
                    }

                    Spacer(Modifier.height(16.dp))
                    Surface(shape = RoundedCornerShape(20.dp), color = statusColor(t.ticketStatus)) {
                        Text(statusLabel(t.ticketStatus), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    if (t.description != null) {
                        Spacer(Modifier.height(16.dp))
                        Text(t.description, color = Color.Gray.copy(alpha = 0.8f), lineHeight = 22.sp)
                    }

                    // 票种列表
                    Spacer(Modifier.height(24.dp))
                    Text("可选票种", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))

                    tickets.forEach { ticket ->
                        TicketCard(ticket, quantity) { qty -> quantity = qty }
                        Spacer(Modifier.height(8.dp))
                    }

                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun TicketCard(ticket: Ticket, quantity: Int, onQuantityChange: (Int) -> Unit) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(ticketTypeLabel(ticket.type), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(4.dp))
                Text("${sessionLabel(ticket.session)} · ${ticket.court} · ${ticket.round}", color = Color.Gray, fontSize = 13.sp)
                Spacer(Modifier.height(4.dp))
                Text("${ticket.date.take(10)}", color = Color.Gray, fontSize = 13.sp)
                if (ticket.availableCount > 0) {
                    Spacer(Modifier.height(4.dp))
                    Text("剩余 ${ticket.availableCount} 张", color = if (ticket.availableCount < 100) Color(0xFFF44336) else Color.Gray, fontSize = 12.sp)
                } else if (ticket.availableCount == 0) {
                    Text("已售罄", color = Color(0xFFF44336), fontSize = 12.sp)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("${ticket.currency} ${ticket.price.toInt()}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                if (ticket.isHot) {
                    Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFF44336).copy(alpha = 0.1f)) {
                        Text("热门", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = Color(0xFFF44336), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailInfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
            Spacer(Modifier.width(4.dp))
            Text(label, color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(Modifier.height(2.dp))
        Text(value, fontWeight = FontWeight.W600, fontSize = 14.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
}

fun ticketTypeLabel(type: TicketType): String = when (type) {
    TicketType.GROUND_PASS -> "Ground Pass"; TicketType.RESERVED_SEAT -> "Reserved Seat"
    TicketType.HOSPITALITY -> "Hospitality"; TicketType.VIP -> "VIP"; TicketType.PREMIUM -> "Premium"
}
fun sessionLabel(session: TicketSession): String = when (session) {
    TicketSession.DAY -> "日场"; TicketSession.NIGHT -> "夜场"; TicketSession.FULL_DAY -> "全日"; TicketSession.WEEKEND -> "周末"
}
