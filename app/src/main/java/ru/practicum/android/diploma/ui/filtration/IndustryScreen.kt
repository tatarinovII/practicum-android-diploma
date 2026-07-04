package ru.practicum.android.diploma.ui.filtration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.IndustryViewModel
import ru.practicum.android.diploma.ui.components.SearchInput
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.MyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndustryScreen(
    navController: NavController,
    viewModel: IndustryViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MyAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            IndustryTopAppBar(navController)
            SearchInput(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChanged(it) },
                onClear = { viewModel.onSearchQueryChanged("") },
                placeholder = stringResource(R.string.enter_industry)
            )
            Box(modifier = Modifier.weight(1f)) {
                when {
                    uiState.isLoading -> IndustryLoading()
                    uiState.error != null -> IndustryError(uiState.error)
                    else -> IndustryContent(
                        industries = uiState.filteredIndustries,
                        selectedIndustryId = uiState.selectedIndustryId,
                        onIndustrySelected = { viewModel.onIndustrySelected(it) }
                    )
                }
            }
            if (uiState.selectedIndustryId != null) {
                IndustryConfirmButton(
                    onClick = {
                        viewModel.confirmSelection()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IndustryTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.select_industry),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_24px),
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun IndustryLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun IndustryError(error: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = error ?: stringResource(R.string.server_error),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun IndustryContent(
    industries: List<ru.practicum.android.diploma.domain.models.FilterIndustry>,
    selectedIndustryId: Int?,
    onIndustrySelected: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 8.dp)
    ) {
        items(industries) { industry ->
            IndustryItem(
                industry = industry,
                isSelected = selectedIndustryId == industry.id,
                onClick = { onIndustrySelected(industry.id) }
            )
        }
    }
}

@Composable
private fun IndustryItem(
    industry: ru.practicum.android.diploma.domain.models.FilterIndustry,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = industry.name,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.width(4.dp))
        val iconRes = if (isSelected) {
            R.drawable.ic_radio_button_on__24px
        } else {
            R.drawable.ic_radio_button_off__24px
        }
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Blue,
            modifier = Modifier.padding(end = 4.dp)
        )
    }
}

@Composable
private fun IndustryConfirmButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
            .height(59.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Blue)
    ) {
        Text(
            text = stringResource(R.string.select),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
