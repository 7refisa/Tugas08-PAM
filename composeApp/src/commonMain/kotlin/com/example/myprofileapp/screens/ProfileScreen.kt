package com.example.myprofileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.viewmodel.ProfileViewModel
import com.example.myprofileapp.viewmodel.NotesViewModel
import org.koin.compose.koinInject
import com.example.myprofileapp.data.local.DeviceInfo
import com.example.myprofileapp.data.local.BatteryInfo
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.context.GlobalContext

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel { GlobalContext.get().get() },
    notesViewModel: NotesViewModel = viewModel { GlobalContext.get().get() }
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val notes by notesViewModel.notes.collectAsState()
    val favorites by notesViewModel.favoriteNotes.collectAsState()

    val deviceInfo: DeviceInfo = koinInject()
    val batteryInfo: BatteryInfo = koinInject()

    ProfileContent(
        totalNotes     = notes.size,
        totalFavorites = favorites.size,
        isDarkMode     = uiState.isDarkMode,
        onThemeToggle  = { profileViewModel.toggleDarkMode() },
        deviceInfo     = deviceInfo,
        batteryInfo    = batteryInfo
    )
}

@Composable
fun ProfileContent(
    totalNotes: Int,
    totalFavorites: Int,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    deviceInfo: DeviceInfo,
    batteryInfo: BatteryInfo
) {
    val darkTextColor = androidx.compose.ui.graphics.Color(0xFF1A1C1E)
    val lightTextColor = androidx.compose.ui.graphics.Color(0xFF42474E)

    Scaffold(
        topBar = {
            NotesTopBar(title = "Profil")
        }
    ) { innerPadding ->
        Column(
            modifier              = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment   = Alignment.CenterHorizontally,
            verticalArrangement   = Arrangement.spacedBy(20.dp)
        ) {
            // ── Avatar placeholder ────────────────────────────────────────
            Box(
                modifier         = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = "👤",
                    style = MaterialTheme.typography.displaySmall
                )
            }

            // ── Nama & email ──────────────────────────────────────────────
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text       = "Mahasiswa ITERA",
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text  = "mahasiswa@student.itera.ac.id",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            HorizontalDivider()

            // ── Statistik catatan ─────────────────────────────────────────
            Text(
                text       = "Statistik",
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier   = Modifier.align(Alignment.Start)
            )

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label          = "Total Catatan",
                    value          = totalNotes.toString(),
                    emoji          = "📝",
                    containerColor = androidx.compose.ui.graphics.Color(0xFFE3F2FD), // Light Blue
                    modifier       = Modifier.weight(1f)
                )
                StatCard(
                    label          = "Favorit",
                    value          = totalFavorites.toString(),
                    emoji          = "❤️",
                    containerColor = androidx.compose.ui.graphics.Color(0xFFFCE4EC), // Light Pink
                    modifier       = Modifier.weight(1f)
                )
            }

            HorizontalDivider()

            // ── Pengaturan (Theme Switch) ───────────────────────────────
            Text(
                text       = "Pengaturan",
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier   = Modifier.align(Alignment.Start)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFFFFF3E0) // Light Yellow
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🌙", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Mode Gelap",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = darkTextColor
                        )
                    }
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { onThemeToggle() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }

            HorizontalDivider()

            // ── Info aplikasi ─────────────────────────────────────────────
            Text(
                text       = "Tentang Aplikasi",
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier   = Modifier.align(Alignment.Start)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(16.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = androidx.compose.ui.graphics.Color(0xFFE8F5E9) // Light Green
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier            = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InfoRow(label = "Mata Kuliah", value = "Pengembangan Aplikasi Mobile", labelColor = lightTextColor, valueColor = darkTextColor)
                    InfoRow(label = "Pertemuan",   value = "8 — Platform Features", labelColor = lightTextColor, valueColor = darkTextColor)
                    InfoRow(label = "Framework",   value = "Compose Multiplatform", labelColor = lightTextColor, valueColor = darkTextColor)
                    InfoRow(label = "Versi App",   value = deviceInfo.getAppVersion(), labelColor = lightTextColor, valueColor = darkTextColor)
                    
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = darkTextColor.copy(alpha = 0.15f))
                    
                    InfoRow(label = "Perangkat",   value = deviceInfo.getDeviceName(), labelColor = lightTextColor, valueColor = darkTextColor)
                    InfoRow(label = "Versi OS",     value = deviceInfo.getOsVersion(), labelColor = lightTextColor, valueColor = darkTextColor)
                    
                    val batteryLevel = batteryInfo.getBatteryLevel()
                    val batteryText = if (batteryLevel >= 0) {
                        "$batteryLevel% ${if (batteryInfo.isCharging()) "(Mengisi Daya ⚡)" else ""}"
                    } else {
                        "Tidak Diketahui"
                    }
                    InfoRow(label = "Baterai",     value = batteryText, labelColor = lightTextColor, valueColor = darkTextColor)
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    emoji: String,
    containerColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    val darkTextColor = androidx.compose.ui.graphics.Color(0xFF1A1C1E)

    Card(
        modifier = modifier,
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = emoji, style = MaterialTheme.typography.titleLarge)
            Text(
                text       = value,
                style      = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color      = darkTextColor
            )
            Text(
                text  = label,
                style = MaterialTheme.typography.labelSmall,
                color = darkTextColor.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    labelColor: androidx.compose.ui.graphics.Color,
    valueColor: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text  = label,
            style = MaterialTheme.typography.bodySmall,
            color = labelColor
        )
        Text(
            text       = value,
            style      = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color      = valueColor
        )
    }
}