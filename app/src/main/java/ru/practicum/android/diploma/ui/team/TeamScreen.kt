package ru.practicum.android.diploma.ui.team

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.MyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen() {

    val developers = listOf(
        "Максим Квасов",
        "Михаил Абрамук",
        "Александр Орлов",
        "Илья Татаринов",
        "Кирилл Москаленко"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            modifier = Modifier.padding(top = 24.dp),
            title = { Text(
                modifier = Modifier.padding(
                    top = 19.dp,
                    bottom = 19.dp,
                    end = 8.dp),
                text = stringResource(id = R.string.team),
                style = MaterialTheme.typography.titleLarge
            )}
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp),
            text = stringResource(id = R.string.who_worked),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))
        GetDevelopers(developers)

    }
}

@Composable
fun GetDevelopers(developers: List<String>) {
    for (developer in developers) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = developer,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun TeamScreenPreview() {
    MyAppTheme(false, { TeamScreen() })
}
