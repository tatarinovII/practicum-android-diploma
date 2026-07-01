package ru.practicum.android.diploma.ui.navigation

enum class Route {
    MAIN,
    FAVORITES,
    TEAM,
    FILTER,
    FILTER_AREA,
    FILTER_INDUSTRY,
    VACANCY
}


val bottomBarRoute = setOf(
    Route.MAIN.name,
    Route.FAVORITES.name,
    Route.TEAM.name
)
