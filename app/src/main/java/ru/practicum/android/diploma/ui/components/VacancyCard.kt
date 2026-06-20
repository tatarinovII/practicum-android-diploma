package ru.practicum.android.diploma.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.theme.Grey
import ru.practicum.android.diploma.util.formatSalary

@Composable
fun VacancyCard(
    vacancy: Vacancy,
    onClick: () -> Unit,
    imageLoader: ImageLoader
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 9.dp, horizontal = 16.dp),
        ) {
            AsyncImage(
                model = vacancy.logo,
                imageLoader = imageLoader,
                contentDescription = stringResource(R.string.company_logo),
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.placeholder_32px),
                error = painterResource(R.drawable.placeholder_32px),
                fallback = painterResource(R.drawable.placeholder_32px),
                onError = { state ->
                    Log.e("VacancyCard", "Error loading ${vacancy.logo}: ${state.result.throwable}")
                }
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                val locationText = vacancy.city?.let { ", $it" } ?: ""
                Text(
                    text = "${vacancy.name}$locationText",
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    color = colorScheme.primary
                )
                if (!vacancy.company.isNullOrEmpty()) {
                    Text(
                        text = vacancy.company,
                        style = MaterialTheme.typography.titleSmall,
                        color = Grey,
                        maxLines = 1
                    )
                }
                Text(
                    text = vacancy.salary?.formatSalary() ?: stringResource(R.string.salary_not_specified),
                    style = MaterialTheme.typography.titleSmall,
                    color = colorScheme.primary
                )
            }
        }
    }
}
