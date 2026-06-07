package com.example.myprofileapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myprofileapp.navigation.BottomNavItem
import com.example.myprofileapp.navigation.Screen
import com.example.myprofileapp.screens.AddNoteScreen
import com.example.myprofileapp.screens.EditNoteScreen
import com.example.myprofileapp.screens.FavoritesScreen
import com.example.myprofileapp.screens.NoteDetailScreen
import com.example.myprofileapp.screens.NoteListScreen
import com.example.myprofileapp.screens.ProfileScreen
import com.example.myprofileapp.viewmodel.ProfileViewModel

import org.koin.core.context.GlobalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun App(
    profileViewModel: ProfileViewModel = viewModel { GlobalContext.get().get() }
) {
    val uiState by profileViewModel.uiState.collectAsState()

    val darkColorScheme = darkColorScheme(
        primary = Color(0xFFBB86FC),
        secondary = Color(0xFF03DAC6),
        background = Color(0xFF121212),
        surface = Color(0xFF1E1E1E),
        onPrimary = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White
    )

    val lightColorScheme = lightColorScheme(
        primary = Color(0xFF6200EE),
        secondary = Color(0xFF03DAC6),
        background = Color(0xFFF5F5F5),
        surface = Color.White,
        onPrimary = Color.White,
        onBackground = Color.Black,
        onSurface = Color.Black
    )

    val colorScheme = if (uiState.isDarkMode) darkColorScheme else lightColorScheme

    MaterialTheme(colorScheme = colorScheme) {
        AppNavigation()
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val currentRoute = navController
                .currentBackStackEntryAsState().value?.destination?.route

            if (currentRoute in BottomNavItem.routes) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController    = navController,
            startDestination = Screen.NoteList.route
        ) {

            // TAB 1 — NoteList
            composable(route = Screen.NoteList.route) {
                NoteListScreen(
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    },
                    onAddClick = {
                        navController.navigate(Screen.AddNote.route)
                    }
                )
            }

            // TAB 2 — Favorites
            composable(route = Screen.Favorites.route) {
                FavoritesScreen(
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.NoteDetail.createRoute(noteId))
                    }
                )
            }

            // TAB 3 — Profile
            composable(route = Screen.Profile.route) {
                ProfileScreen()
            }

            // ADD NOTE
            composable(route = Screen.AddNote.route) {
                AddNoteScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            // NOTE DETAIL
            composable(
                route     = Screen.NoteDetail.route,
                arguments = listOf(
                    navArgument("noteId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L

                NoteDetailScreen(
                    noteId      = noteId,
                    onBack      = { navController.popBackStack() },
                    onEditClick = { id ->
                        navController.navigate(Screen.EditNote.createRoute(id))
                    }
                )
            }

            // EDIT NOTE
            composable(
                route     = Screen.EditNote.route,
                arguments = listOf(
                    navArgument("noteId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L

                EditNoteScreen(
                    noteId = noteId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute      = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavItem.items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick  = {
                    navController.navigate(item.route) {
                        popUpTo(Screen.NoteList.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                icon  = {
                    Icon(
                        imageVector        = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) }
            )
        }
    }
}