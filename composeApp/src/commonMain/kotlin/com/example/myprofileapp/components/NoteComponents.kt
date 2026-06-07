package com.example.myprofileapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.data.Note

/**
 * Card catatan dengan tombol toggle favorit (❤️).
 *
 * @param note             Data catatan.
 * @param onClick          Navigasi ke detail.
 * @param onToggleFavorite Toggle like/unlike.
 */
@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    index: Int,
    modifier: Modifier = Modifier
) {
    val pastelColors = listOf(
        androidx.compose.ui.graphics.Color(0xFFFCE4EC), // Light Pink (#FCE4EC)
        androidx.compose.ui.graphics.Color(0xFFFFF3E0), // Light Orange/Yellow (#FFF3E0)
        androidx.compose.ui.graphics.Color(0xFFE3F2FD), // Light Blue (#E3F2FD)
        androidx.compose.ui.graphics.Color(0xFFE8F5E9)  // Light Green (#E8F5E9)
    )
    val cardColor = pastelColors[index % pastelColors.size]
    
    // Clean dark text colors for high readability on pastel backgrounds
    val titleColor = androidx.compose.ui.graphics.Color(0xFF1A1C1E)
    val contentColor = androidx.compose.ui.graphics.Color(0xFF42474E)
    val dateColor = androidx.compose.ui.graphics.Color(0xFF565F69)

    Card(
        modifier  = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = note.title,
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color      = titleColor,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text     = note.content,
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = contentColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text     = com.example.myprofileapp.utils.formatEpochMillis(note.createdAt),
                    style    = MaterialTheme.typography.labelSmall,
                    color    = dateColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Tombol toggle favorit
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector        = if (note.isFavorite) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = if (note.isFavorite) "Hapus dari favorit"
                    else "Tambah ke favorit",
                    tint               = if (note.isFavorite) androidx.compose.ui.graphics.Color(0xFFE91E63)
                    else titleColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text       = title,
                fontWeight = FontWeight.Bold,
                maxLines   = 1,
                overflow   = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                }
            }
        },
        actions = actions,
        colors  = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun EditActionButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.Default.Edit, contentDescription = "Edit")
    }
}

@Composable
fun FavoriteActionButton(isFavorite: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector        = if (isFavorite) Icons.Default.Favorite
            else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorite) "Hapus dari favorit" else "Tambah ke favorit",
            tint               = if (isFavorite) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun EmptyState(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier         = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("📝", style = MaterialTheme.typography.displayMedium)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text  = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}