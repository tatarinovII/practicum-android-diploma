package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.practicum.android.diploma.ui.navigation.Route
import ru.practicum.android.diploma.ui.theme.MyAppTheme

@Composable
fun MainScreen(navController: NavController) {
    MyAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Поиск вакансий")
            Button(onClick = {
                navController.navigate(Route.FILTER.name)
            }) { Text("Открыть фильтрацию") }

            Button(onClick = {
                navController.navigate("${Route.VACANCY.name}/Мороженщик")
            }) { Text("Вакансия Мороженщик") }

            Button(onClick = {
                navController.navigate("${Route.VACANCY.name}/Директор фабрики мороженного")
            }) { Text("Директор фабрики мороженного") }
        }
    }
}
