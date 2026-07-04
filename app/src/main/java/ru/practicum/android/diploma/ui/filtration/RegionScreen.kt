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
import ru.practicum.android.diploma.presentation.filter.RegionUiState
import ru.practicum.android.diploma.presentation.filter.RegionViewModel
import ru.practicum.android.diploma.ui.navigation.Route
import ru.practicum.android.diploma.ui.theme.MyAppTheme
import ru.practicum.android.diploma.ui.vacancy.ShowLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionScreen(
    navController: NavController,
    viewModel: RegionViewModel = koinViewModel()
) {
    val uiRegionState by viewModel.uiRegionState.collectAsState()

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
                        text = stringResource(R.string.choice_region),
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
                when(uiRegionState) {
                    is RegionUiState.Content -> {
                        ShowRegionContent(
                            regions = (uiRegionState as RegionUiState.Content).regions as List<FilterArea>,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    is RegionUiState.Error -> {
                        ShowAreaPlaceholder()
                    }
                    is RegionUiState.Loading -> {
                        ShowLoading()
                    }
                }
            }
        }
    }
}
@Composable
fun ShowRegionContent(
    regions: List<FilterArea>,
    navController: NavController,
    viewModel: RegionViewModel
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp)
    ) {
        RegionContent(
            regions = regions,
            onRegionClick = {
                viewModel.onRegionSelected(it)
                navController.navigate(Route.AREA.name)
            })
    }
}
@Composable
fun RegionContent(
    regions: List<FilterArea>,
    onRegionClick: (FilterArea) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items = regions) { region ->
            RegionItem(
                region = region.name,
                onRegionClick = { onRegionClick(region) }
            )
        }
    }
}

@Composable
fun RegionItem(
    region: String,
    onRegionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp)
            .clickable { onRegionClick() }
    ) {
        Text(
            text = region,
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
