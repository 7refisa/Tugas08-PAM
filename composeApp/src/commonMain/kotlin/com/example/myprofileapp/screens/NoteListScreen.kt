package com.example.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myprofileapp.components.EmptyState
import com.example.myprofileapp.components.NoteCard
import com.example.myprofileapp.components.NotesTopBar
import com.example.myprofileapp.data.local.SettingsManager
import com.example.myprofileapp.viewmodel.NotesViewModel
import org.koin.compose.koinInject
import com.example.myprofileapp.data.local.NetworkMonitor
import androidx.compose.animation.AnimatedVisibility
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.context.GlobalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    onNoteClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    viewModel: NotesViewModel = viewModel { GlobalContext.get().get() }
) {
    val notes by viewModel.notes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()

    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NotesTopBar(
                title = "Catatan Saya",
                actions = {
                    Box {
                        IconButton(onClick = { showSortMenu = true }) {
                            Icon(Icons.Default.Sort, contentDescription = "Urutkan")
                        }
                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Terbaru Dahulu") },
                                onClick = {
                                    viewModel.setSortOrder(SettingsManager.SORT_NEWEST)
                                    showSortMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Terlama Dahulu") },
                                onClick = {
                                    viewModel.setSortOrder(SettingsManager.SORT_OLDEST)
                                    showSortMenu = false
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick        = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor   = MaterialTheme.colorScheme.onPrimary,
                modifier       = Modifier.padding(bottom = 56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah catatan")
            }
        }
    ) { innerPadding ->
        val networkMonitor: NetworkMonitor = koinInject()
        val isConnected by networkMonitor.observeConnectivity().collectAsState(initial = true)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedVisibility(visible = !isConnected) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.errorContainer
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🔌 Sedang Offline — Tidak Ada Koneksi Internet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Cari catatan...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Cari") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            if (notes.isEmpty()) {
                EmptyState(
                    message  = if (searchQuery.isEmpty()) "Belum ada catatan.\nTekan + untuk mulai menulis!" else "Tidak ada catatan yang cocok.",
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier            = Modifier.weight(1f),
                    contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                itemsIndexed(notes, key = { _, note -> note.id }) { index, note ->
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
}