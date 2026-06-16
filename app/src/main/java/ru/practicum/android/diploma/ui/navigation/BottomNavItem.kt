package ru.practicum.android.diploma.ui.navigation

import ru.practicum.android.diploma.R

data class BottomNavItem(
    val route: Route,
    val iconRes: Int,
    val titleRes: Int
)

val bottomNavItems = listOf(
    BottomNavItem(Route.Main, R.drawable.ic_main_24px, R.string.title_main),
    BottomNavItem(Route.Favorites, R.drawable.ic_favorites_on__24px, R.string.title_favorites),
    BottomNavItem(Route.Team, R.drawable.ic_team_24px, R.string.title_team)
)
