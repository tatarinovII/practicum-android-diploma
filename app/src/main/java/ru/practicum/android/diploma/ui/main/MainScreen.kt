package ru.practicum.android.diploma.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.ImageLoader
import coil.decode.SvgDecoder
import okhttp3.OkHttpClient
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.main.SearchScreenState
import ru.practicum.android.diploma.presentation.main.SearchViewModel
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.LoadingIndicator
import ru.practicum.android.diploma.ui.components.SearchInput
import ru.practicum.android.diploma.ui.components.VacancyList
import ru.practicum.android.diploma.ui.navigation.Route
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.MyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: SearchViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val isFilterActive by viewModel.isFilterActive.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {
        viewModel.refreshFilterState()
    }

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
                            painter = painterResource(
                                if (isFilterActive) R.drawable.ic_filter_on__24px else R.drawable.ic_filter_off__24px
                            ),
                            contentDescription = stringResource(R.string.filter),
                            tint = if (isFilterActive) Color.Unspecified else MaterialTheme.colorScheme.primary
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
                onClear = { viewModel.clearQuery() },
                placeholder = stringResource(R.string.search_hint)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (val screenState = uiState.screenState) {
                    is SearchScreenState.Initial -> {
                        InitialPlaceholder()
                    }
                    is SearchScreenState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingIndicator()
                        }
                    }
                    is SearchScreenState.Content -> {
                        val counterHeight = 38.dp
                        VacancyList(
                            vacancies = screenState.vacancies,
                            onVacancyClick = { vacancyId ->
                                navController.navigate("${Route.VACANCY.name}/$vacancyId")
                            },
                            isLoadingMore = uiState.isLoadingMore,
                            onLoadMore = { viewModel.loadNextPage() },
                            imageLoader = imageLoader,
                            topPadding = counterHeight
                        )
                        VacancyCounter(
                            count = screenState.totalFound,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .zIndex(1f)
                        )
                    }
                    is SearchScreenState.EmptyResult -> {
                        Placeholder(
                            iconRes = R.drawable.placeholder_not_found,
                            message = stringResource(R.string.not_found)
                        )
                        VacancyCounter(
                            count = 0,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .zIndex(1f)
                        )
                    }
                    is SearchScreenState.Error -> {
                        when (screenState) {
                            SearchScreenState.Error.NoInternet -> {
                                Placeholder(
                                    iconRes = R.drawable.placeholder_no_connection,
                                    message = stringResource(R.string.no_internet)
                                )
                            }
                            SearchScreenState.Error.ServerError -> {
                                Placeholder(
                                    iconRes = R.drawable.placeholder_server_error,
                                    message = stringResource(R.string.server_error)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InitialPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.placeholder_main),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .aspectRatio(3f / 2f),
                contentScale = ContentScale.Crop
            )
        }
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
            text = if (count > 0) "Найдено $count вакансий" else "Таких вакансий нет",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
