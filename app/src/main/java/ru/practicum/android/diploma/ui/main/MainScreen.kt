package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import okhttp3.OkHttpClient
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.main.SearchViewModel
import ru.practicum.android.diploma.ui.components.LoadingIndicator
import ru.practicum.android.diploma.ui.components.VacancyList
import ru.practicum.android.diploma.ui.navigation.Route
import ru.practicum.android.diploma.ui.theme.Black
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.Grey
import ru.practicum.android.diploma.ui.theme.MyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: SearchViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            }
            .build()
    }

    MyAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_search),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Route.FILTER.name) },
                        modifier = Modifier.size(48.dp).padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_filter_off__24px),
                            contentDescription = stringResource(R.string.filter),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.height(65.dp)
            )

            SearchInput(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.updateQuery(it) },
                onClear = { viewModel.clearQuery() }
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.searchQuery.isEmpty() -> {
                        EmptyPlaceholder(text = stringResource(R.string.search_placeholder_hint))
                    }
                    uiState.isLoading && uiState.vacancies.isEmpty() -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingIndicator()
                        }
                    }
                    uiState.vacancies.isEmpty() -> {
                        EmptyPlaceholder(text = stringResource(R.string.search_empty_result))
                    }
                    else -> {
                        val counterHeight = 38.dp
                        VacancyList(
                            vacancies = uiState.vacancies,
                            onVacancyClick = { vacancyId ->
                                navController.navigate("${Route.VACANCY.name}/$vacancyId")
                            },
                            isLoadingMore = uiState.isLoadingMore,
                            onLoadMore = { viewModel.loadNextPage() },
                            imageLoader = imageLoader,
                            topPadding = counterHeight
                        )
                    }
                }

                if (uiState.vacancies.isNotEmpty()) {
                    VacancyCounter(
                        count = uiState.totalFound,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .zIndex(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchInput(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxSize()
                .height(56.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_hint),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    style = MaterialTheme.typography.titleSmall
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = onClear,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close_24px),
                            contentDescription = stringResource(R.string.clear_query),
                            tint = Black
                        )
                    }
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_search_24px),
                        contentDescription = stringResource(R.string.search),
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 4.dp),
                        tint = Black
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
private fun EmptyPlaceholder(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Grey
        )
    }
}

@Composable
private fun VacancyCounter(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(top = 3.dp)
            .background(
                color = Blue,
                shape = RoundedCornerShape(12.dp)
            )
            .height(27.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Найдено $count вакансий",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
