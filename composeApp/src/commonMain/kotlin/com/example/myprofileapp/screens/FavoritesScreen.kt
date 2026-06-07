package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.core.context.GlobalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myprofileapp.components.EmptyState
import com.example.myprofileapp.components.NoteCard
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.viewmodel.NotesViewModel

@Composable
fun FavoritesScreen(
    onNoteClick: (Long) -> Unit,
    viewModel: NotesViewModel = viewModel { GlobalContext.get().get() }
) {
    val favorites by viewModel.favoriteNotes.collectAsState()

    Scaffold(
        topBar = { NotesTopBar(title = "Favorit") }
    ) { innerPadding ->
        if (favorites.isEmpty()) {
            EmptyState(
                message  = "Belum ada catatan favorit.\nTekan ♥ pada catatan untuk menyimpannya.",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier            = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(favorites, key = { _, note -> note.id }) { index, note ->
                    NoteCard(
                        note             = note,
                        onClick          = { onNoteClick(note.id) },
                        onToggleFavorite = { viewModel.toggleFavorite(note.id) },
                        index            = index
                    )
                }
            }
        }
    }
}