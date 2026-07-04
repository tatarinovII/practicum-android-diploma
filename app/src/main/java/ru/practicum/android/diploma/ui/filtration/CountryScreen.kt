package ru.practicum.android.diploma.ui.filtration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.presentation.filter.CountryUiState
import ru.practicum.android.diploma.presentation.filter.CountryViewModel
import ru.practicum.android.diploma.ui.navigation.Route
import ru.practicum.android.diploma.ui.theme.MyAppTheme
import ru.practicum.android.diploma.ui.vacancy.ShowLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryScreen(
    navController: NavController,
    viewModel: CountryViewModel = koinViewModel()
) {
    val uiCountryState by viewModel.uiCountryState.collectAsState()
    MyAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(
                            top = 19.dp,
                            bottom = 19.dp,
                            start = 4.dp,
                            end = 8.dp
                        ),
                        text = stringResource(R.string.choice_country),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(
                            vertical = 8.dp,
                            horizontal = 4.dp
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_24px),
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                when(uiCountryState) {
                    is CountryUiState.Content -> {
                        ShowCountryContent(
                            countries = (uiCountryState as CountryUiState.Content).countries as List<FilterArea>,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    is CountryUiState.Error -> {
                        ShowAreaPlaceholder()
                    }
                    is CountryUiState.Loading -> {
                        ShowLoading()
                    }
                }
            }
        }
    }
}
@Composable
fun ShowCountryContent(
    countries: List<FilterArea>,
    navController: NavController,
    viewModel: CountryViewModel
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp)
    ) {
        CountryContent(
            countries = countries,
            onCountryClick = {
                viewModel.onCountrySelected(it)
                navController.navigate(Route.AREA.name)
            })
    }
}
@Composable
fun CountryContent(
    countries: List<FilterArea>,
    onCountryClick: (FilterArea) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items = countries) { country ->
            CountryItem(
                country = country.name,
                onCountryClick = { onCountryClick(country) }
            )
        }
    }
}
@Composable
fun CountryItem(
    country: String,
    onCountryClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp)
            .clickable { onCountryClick() }
    ) {
        Text(
            text = country,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward_24px),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

