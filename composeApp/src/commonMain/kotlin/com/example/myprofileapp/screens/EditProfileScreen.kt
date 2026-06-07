package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditProfileScreen(
    currentName : String,
    currentBio  : String,
    onSave      : (String, String) -> Unit,
    onCancel    : () -> Unit
) {
    // State lokal untuk TextField
    var nameInput by remember(currentName) { mutableStateOf(currentName) }
    var bioInput  by remember(currentBio)  { mutableStateOf(currentBio) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onCancel) {
                Icon(
                    imageVector        = Icons.Rounded.ArrowBack,
                    contentDescription = "Kembali"
                )
            }
            Text(
                text     = "Edit Profil",
                fontSize = 20.sp,
                style    = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(24.dp))

        // TextField Nama — stateless, pakai state hoisting
        EditTextField(
            label       = "Nama",
            value       = nameInput,
            onValueChange = { nameInput = it },
            placeholder = "Masukkan nama kamu"
        )

        Spacer(Modifier.height(16.dp))

        // TextField Bio — stateless, reuse komponen yang sama
        EditTextField(
            label         = "Bio",
            value         = bioInput,
            onValueChange = { bioInput = it },
            placeholder   = "Ceritakan tentang dirimu...",
            singleLine    = false,
            minLines      = 3
        )

        Spacer(Modifier.height(32.dp))

        // Tombol Simpan
        Button(
            onClick  = { onSave(nameInput, bioInput) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape    = RoundedCornerShape(12.dp),
            enabled  = nameInput.isNotBlank()
        ) {
            Text("Simpan")
        }

        Spacer(Modifier.height(12.dp))

        // Tombol Batal
        OutlinedButton(
            onClick  = onCancel,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape    = RoundedCornerShape(12.dp)
        ) {
            Text("Batal")
        }
    }
}

@Composable
fun EditTextField(
    label         : String,
    value         : String,
    onValueChange : (String) -> Unit,
    placeholder   : String  = "",
    singleLine    : Boolean = true,
    minLines      : Int     = 1
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        label         = { Text(label) },
        placeholder   = { Text(placeholder) },
        singleLine    = singleLine,
        minLines      = minLines,
        shape         = RoundedCornerShape(12.dp),
        modifier      = Modifier.fillMaxWidth()
    )
}
