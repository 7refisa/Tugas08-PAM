package com.example.myprofileapp.navigation

/**
 * Mendefinisikan semua Routes secara terpusat dan type-safe.
 * Menggunakan sealed class agar setiap destination bersifat eksplisit
 * dan compiler dapat mendeteksi jika ada yang terlewat.
 *
 * Best Practice: Semua routes didefinisikan di satu tempat saja.
 */
sealed class Screen(val route: String) {

    // ── Bottom Navigation Tabs ──────────────────────────────────────────────

    /** Tab utama: daftar semua catatan */
    object NoteList : Screen("note_list")

    /** Tab favorit: catatan yang di-favorit-kan */
    object Favorites : Screen("favorites")

    /** Tab profil pengguna */
    object Profile : Screen("profile")

    // ── Detail & Form Screens ───────────────────────────────────────────────

    /** Screen untuk menambah catatan baru (tanpa argument) */
    object AddNote : Screen("add_note")

    /**
     * Screen detail catatan.
     * Route pattern: "note_detail/{noteId}"
     * - {noteId} adalah Required Argument bertipe Int.
     */
    object NoteDetail : Screen("note_detail/{noteId}") {
        /**
         * Helper function untuk membuat route dengan nilai noteId yang
         * sesungguhnya, contoh: createRoute(5) → "note_detail/5"
         */
        fun createRoute(noteId: Long): String = "note_detail/$noteId"
    }

    /**
     * Screen edit catatan.
     * Route pattern: "edit_note/{noteId}"
     * - {noteId} adalah Required Argument bertipe Int.
     */
    object EditNote : Screen("edit_note/{noteId}") {
        /** Helper function: createRoute(5) → "edit_note/5" */
        fun createRoute(noteId: Long): String = "edit_note/$noteId"
    }
}