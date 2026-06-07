package com.example.myprofileapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Mendefinisikan item-item Bottom Navigation Bar.
 *
 * Setiap item memiliki:
 * - [route]  : String route yang sesuai dengan Screen sealed class
 * - [icon]   : Ikon Material yang ditampilkan di tab bar
 * - [label]  : Label teks di bawah ikon
 */
sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Notes : BottomNavItem(
        route = Screen.NoteList.route,
        icon  = Icons.Default.Home,
        label = "Notes"
    )

    object Favorites : BottomNavItem(
        route = Screen.Favorites.route,
        icon  = Icons.Default.Favorite,
        label = "Favorites"
    )

    object Profile : BottomNavItem(
        route = Screen.Profile.route,
        icon  = Icons.Default.Person,
        label = "Profile"
    )

    companion object {
        /**
         * Daftar tab yang ditampilkan di NavigationBar, sesuai urutan tampilan.
         */
        val items: List<BottomNavItem> = listOf(Notes, Favorites, Profile)

        /**
         * Kumpulan route milik bottom-nav tabs.
         * Digunakan untuk menentukan apakah NavigationBar harus ditampilkan
         * (hanya tampil di top-level destinations / tab screens).
         */
        val routes: Set<String> = items.map { it.route }.toSet()
    }
}