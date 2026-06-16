package ru.practicum.android.diploma.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.practicum.android.diploma.ui.favorites.FavoritesScreen
import ru.practicum.android.diploma.ui.main.MainScreen
import ru.practicum.android.diploma.ui.team.TeamScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            } else {
                null
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Main.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Main.route) { MainScreen() }
            composable(Route.Favorites.route) { FavoritesScreen() }
            composable(Route.Team.route) { TeamScreen() }

            // Экраны без нижней панели
            composable(Route.Filters.route) {
                // TODO: FiltersScreen()
            }
            composable(
                route = Route.VacancyDetail.route,
                arguments = listOf(navArgument("vacancyId") { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("vacancyId")?.let { vacancyId ->
                    // TODO: VacancyDetailScreen(vacancyId)
                }
            }
        }
    }
}
