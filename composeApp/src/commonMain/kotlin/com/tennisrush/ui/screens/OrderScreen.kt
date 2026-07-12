package com.tennisrush.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tennisrush.data.OrderRepository
import com.tennisrush.model.*

@Composable
fun OrderScreen() {
    var orders by remember { mutableStateOf<List<Order>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        orders = OrderRepository.getOrders("current_user")
        isLoading = false
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    if (orders.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.ConfirmationNumber, contentDescription = null, modifier = Modifier.size(80.dp), tint = Color.Gray.copy(alpha = 0.3f))
                Spacer(Modifier.height(16.dp))
                Text("暂无订单", color = Color.Gray, fontSize = 16.sp)
                Spacer(Modifier.height(8.dp))
                Text("去赛事页面选购门票吧", color = Color.Gray.copy(alpha = 0.6f), fontSize = 14.sp)
            }
        }
        return
    }

    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(orders) { order ->
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Row {
                        Column(Modifier.weight(1f)) {
                            order.tournamentName?.let {
                                Text(it, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(Modifier.height(4.dp))
                            }
                            order.ticketType?.let {
                                Text(it, color = Color.Gray, fontSize = 13.sp)
                                Spacer(Modifier.height(4.dp))
                            }
                            order.sessionDate?.let {
                                Text(it, color = Color.Gray, fontSize = 13.sp)
                            }
                            order.court?.let {
                                Text(it, color = Color.Gray, fontSize = 12.sp)
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("${order.currency} ${order.totalPrice.toInt()}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(Modifier.height(4.dp))
                            Surface(shape = RoundedCornerShape(4.dp), color = orderStatusBg(order.status)) {
                                Text(order.status.name, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp), color = orderStatusColor(order.status), fontSize = 12.sp, fontWeight = FontWeight.W600)
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))
                    Row {
                        Text("订单号：${order.id}", color = Color.Gray.copy(alpha = 0.5f), fontSize = 11.sp)
                        Spacer(Modifier.weight(1f))
                        Text("${order.quantity} 张", color = Color.Gray, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

fun orderStatusColor(status: OrderStatus): Color = when (status) {
    OrderStatus.PENDING -> Color(0xFFFF9800); OrderStatus.CONFIRMED -> Color(0xFF4CAF50); OrderStatus.CANCELLED -> Color.Gray; OrderStatus.REFUNDED -> Color.Gray
}
fun orderStatusBg(status: OrderStatus): Color = when (status) {
    OrderStatus.PENDING -> Color(0xFFFF9800).copy(alpha = 0.12f); OrderStatus.CONFIRMED -> Color(0xFF4CAF50).copy(alpha = 0.12f); OrderStatus.CANCELLED -> Color.Gray.copy(alpha = 0.1f); OrderStatus.REFUNDED -> Color.Gray.copy(alpha = 0.1f)
}
