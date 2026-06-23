package ru.practicum.android.diploma.ui.filtration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.ui.theme.MyAppTheme

@Composable
fun FiltrationScreen() {
    MyAppTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Фильтрация")
        }
    }
}
