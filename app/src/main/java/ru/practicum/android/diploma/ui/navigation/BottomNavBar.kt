package ru.practicum.android.diploma.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.Grey

@Composable
fun BottomNavBar(
    navController: NavHostController,
    showDivider: Boolean = true
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column {
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Grey)
            )
        }
        NavigationBar(
            modifier = Modifier.height(57.dp),
            containerColor = MaterialTheme.colorScheme.secondary
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentDestination?.hierarchy?.any { it.route == item.route.route } == true
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            Icon(
                                painter = painterResource(item.iconRes),
                                contentDescription = stringResource(item.titleRes),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Text(
                                text = stringResource(item.titleRes),
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 0.dp)
                            )
                        }
                    },
                    label = {},
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Blue,
                        unselectedIconColor = Grey,
                        selectedTextColor = Blue,
                        unselectedTextColor = Grey,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
