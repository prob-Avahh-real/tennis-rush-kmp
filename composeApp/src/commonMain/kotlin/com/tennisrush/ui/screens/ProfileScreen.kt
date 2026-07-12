package com.tennisrush.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@Composable
fun ProfileScreen() {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // Header
        Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Outlined.Person, contentDescription = null, modifier = Modifier.size(80.dp), tint = Color.Gray)
            Spacer(Modifier.height(12.dp))
            Text("未登录", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("登录后可查看订单和设置", color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.height(16.dp))
            OutlinedButton(onClick = {}, shape = RoundedCornerShape(24.dp)) {
                Text("登录 / 注册")
            }
        }

        HorizontalDivider()

        // Settings
        Column(Modifier.padding(16.dp)) {
            SettingsItem(Icons.Outlined.Notifications, "通知设置")
            SettingsItem(Icons.Outlined.Payment, "支付方式")
            SettingsItem(Icons.Outlined.Language, "语言")
            SettingsItem(Icons.Outlined.Info, "关于")
        }
    }
}

@Composable
fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Text(title, Modifier.weight(1f), fontSize = 16.sp)
        Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Color.Gray.copy(alpha = 0.4f))
    }
}
