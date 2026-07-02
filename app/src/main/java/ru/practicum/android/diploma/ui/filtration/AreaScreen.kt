package ru.practicum.android.diploma.ui.filtration

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.AreaUiState
import ru.practicum.android.diploma.presentation.filter.AreaViewModel
import ru.practicum.android.diploma.ui.navigation.Route
import ru.practicum.android.diploma.ui.theme.Grey
import ru.practicum.android.diploma.ui.theme.MyAppTheme
import ru.practicum.android.diploma.ui.vacancy.ShowLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaScreen(
    navController: NavController,
    viewModel: AreaViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

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
                        text = stringResource(R.string.choice_work_place),
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
                when(uiState) {
                    is AreaUiState.Content -> {
                        ShowCountries(
                            uiState = uiState,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    is AreaUiState.Error -> {
                        ShowPlaceholder()
                    }
                    AreaUiState.Loading -> {
                        ShowLoading()
                    }
                }
            }
        }
    }
}

@Composable
fun ShowCountries(
    uiState: AreaUiState,
    navController: NavController,
    viewModel: AreaViewModel
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp)) {
        //Spacer(modifier = Modifier.height(16.dp))
        FilterOptionRow(
            label = stringResource(R.string.country),
            value = (uiState as AreaUiState.Content).country ?: "",
            onClick = { navController.navigate(Route.COUNTRY.name) },
            onClear = { viewModel.clearCountry() }
        )

        FilterOptionRow(
            label = stringResource(R.string.region),
            value = (uiState as AreaUiState.Content).region ?: "",
            onClick = { navController.navigate(Route.REGION.name) },
            onClear = { viewModel.clearRegion() }
        )
    }
}

@Composable
fun ShowPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.placeholder_error_to_get_list),
            contentDescription = stringResource(R.string.error_to_get_list)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.error_to_get_list),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun FilterOptionRow(
    label: String,
    value: String,
    onClick: () -> Unit,
    onClear: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (value.isEmpty()) {
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                color = Grey,
                style = MaterialTheme.typography.titleSmall
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_forward_24px),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        } else {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    modifier = Modifier.padding(top = 12.5.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = value,
                    modifier = Modifier.padding(bottom = 12.5.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            IconButton(
                onClick = onClear,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_24px),
                    contentDescription = stringResource(R.string.clear_query),
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
