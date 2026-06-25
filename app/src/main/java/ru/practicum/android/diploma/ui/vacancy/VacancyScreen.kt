package ru.practicum.android.diploma.ui.vacancy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.ui.theme.MyAppTheme

@Composable
fun VacancyScreen(vacancyId: String) {
    MyAppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Вакансия $vacancyId")
        }
    }
}
