package ru.practicum.android.diploma.ui.navigation

sealed class Route(val route: String) {
    object Main : Route("main")
    object Favorites : Route("favorites")
    object Team : Route("team")
    object Filters : Route("filters")
    object VacancyDetail : Route("vacancy/{vacancyId}") // шаблон для composable
}

fun vacancyDetailRoute(vacancyId: String): String = "vacancy/$vacancyId"

val bottomBarRoutes = setOf(
    Route.Main.route,
    Route.Favorites.route,
    Route.Team.route
)
