package ru.practicum.android.diploma.ui.navigation

import ru.practicum.android.diploma.R

data class BottomNavItem(
    val route: String,
    val iconRes: Int,
    val titleRes: Int
)

val bottomNavItems = listOf(
    BottomNavItem(Route.MAIN.name, R.drawable.ic_main_24px, R.string.title_main),
    BottomNavItem(Route.FAVORITES.name, R.drawable.ic_favorites_on__24px, R.string.title_favorites),
    BottomNavItem(Route.TEAM.name, R.drawable.ic_team_24px, R.string.title_team)
)
