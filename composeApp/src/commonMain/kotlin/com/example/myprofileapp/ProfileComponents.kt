package com.example.myprofileapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import myprofileapp.composeapp.generated.resources.Res
import myprofileapp.composeapp.generated.resources.profile_refi
import org.jetbrains.compose.resources.painterResource

// Menerima name dan role sebagai parameter agar bisa update dari ViewModel
@Composable
fun ProfileHeader(
    name : String = "Refi Ikhsanti",
    role : String = "Informatics Student"
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter            = painterResource(Res.drawable.profile_refi),
            contentDescription = "Profile Photo",
            modifier           = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(12.dp))

        Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = role, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
    }
}

// Generic container card
@Composable
fun ProfileCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier  = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content   = content
        )
    }
}

// Satu baris info dengan ikon + label + nilai
// Stateless — semua data dari parameter
@Composable
fun InfoItem(
    icon  : ImageVector,
    label : String,
    value : String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = label)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            Text(text = value, fontSize = 14.sp)
        }
    }
}

// Menerima bio sebagai parameter agar bisa diupdate dari ViewModel
@Composable
fun BioSection(
    bio : String = ""
) {
    Column(
        modifier            = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text       = "UI/UX Enthusiast & Data Explorer",
            fontSize   = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign  = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text      = bio,
            fontSize  = 13.sp,
            textAlign = TextAlign.Center,
            color     = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            lineHeight = 20.sp
        )
    }
}
