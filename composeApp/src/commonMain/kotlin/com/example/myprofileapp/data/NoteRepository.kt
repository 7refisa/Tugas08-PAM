package com.example.myprofileapp.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.myprofileapp.db.NotesDatabase
import com.example.myprofileapp.db.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(private val database: NotesDatabase) {
    private val queries = database.noteQueries

    init {
        // Seed default notes if database is empty
        if (queries.selectAllNotes().executeAsList().isEmpty()) {
            queries.insertNote("Belajar Compose Navigation", "NavHost, NavController, dan Routes adalah tiga komponen inti navigasi di Jetpack Compose.", true, System.currentTimeMillis() - 86400000)
            queries.insertNote("Kotlin Sealed Class", "Sealed class cocok untuk merepresentasikan hierarki terbatas dan routes secara type-safe.", false, System.currentTimeMillis())
        }
    }

    private fun NoteEntity.toNote(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            isFavorite = isFavorite,
            createdAt = createdAt
        )
    }

    /** Ambil semua catatan dengan search query & sort order */
    fun getNotes(searchQuery: String, sortOrder: String): Flow<List<Note>> {
        val flow = if (searchQuery.isBlank()) {
            queries.selectAllNotes().asFlow().mapToList(Dispatchers.Default)
        } else {
            queries.searchNotes("%$searchQuery%").asFlow().mapToList(Dispatchers.Default)
        }
        return flow.map { entities ->
            val notes = entities.map { it.toNote() }
            if (sortOrder == "oldest") {
                notes.sortedBy { it.createdAt }
            } else {
                notes.sortedByDescending { it.createdAt }
            }
        }
    }

    /** Ambil semua catatan favorit */
    fun getFavoriteNotes(searchQuery: String = ""): Flow<List<Note>> {
        val flow = if (searchQuery.isBlank()) {
            queries.selectAllNotes().asFlow().mapToList(Dispatchers.Default)
        } else {
            queries.searchNotes("%$searchQuery%").asFlow().mapToList(Dispatchers.Default)
        }
        return flow.map { entities ->
            entities.map { it.toNote() }.filter { it.isFavorite }.sortedByDescending { it.createdAt }
        }
    }

    /** Tambah catatan baru */
    fun addNote(title: String, content: String) {
        queries.insertNote(title, content, false, System.currentTimeMillis())
    }

    /** Update catatan yang sudah ada berdasarkan ID */
    fun updateNote(id: Long, title: String, content: String) {
        queries.updateNote(title, content, id)
    }

    /** Toggle status favorit */
    fun toggleFavorite(id: Long) {
        val note = findById(id) ?: return
        queries.updateFavoriteStatus(!note.isFavorite, id)
    }

    /** Hapus catatan */
    fun deleteNote(id: Long) {
        queries.deleteNote(id)
    }

    /** Cari catatan berdasarkan ID */
    fun findById(id: Long): Note? {
        return queries.selectNoteById(id).executeAsOneOrNull()?.toNote()
    }
}