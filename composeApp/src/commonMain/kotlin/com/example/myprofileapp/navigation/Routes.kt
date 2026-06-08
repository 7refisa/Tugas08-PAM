package com.example.myprofileapp.navigation

import kotlinx.serialization.Serializable

/** Type-safe route untuk NoteDetail — membawa noteId sebagai Long */
@Serializable
data class NoteDetailRoute(val noteId: Long)

/** Type-safe route untuk EditNote — membawa noteId sebagai Long */
@Serializable
data class EditNoteRoute(val noteId: Long)
