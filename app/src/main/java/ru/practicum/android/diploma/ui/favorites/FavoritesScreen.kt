package ru.practicum.android.diploma.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import okhttp3.OkHttpClient
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.favorites.FavoritesUiState
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.ui.components.Placeholder
import ru.practicum.android.diploma.ui.components.LoadingIndicator
import ru.practicum.android.diploma.ui.components.VacancyList
import ru.practicum.android.diploma.ui.navigation.Route
import ru.practicum.android.diploma.ui.theme.MyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = koinViewModel()
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

    LaunchedEffect(Unit) {
        viewModel.uploadVacancies()
    }

    MyAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_favorites),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.height(65.dp)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (uiState) {
                    is FavoritesUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingIndicator()
                        }
                    }

                    is FavoritesUiState.Content -> {
                        val content = uiState as FavoritesUiState.Content
                        val counterHeight = 38.dp
                        VacancyList(
                            vacancies = content.vacancies,
                            onVacancyClick = { vacancyId ->
                                navController.navigate("${Route.VACANCY.name}/$vacancyId")
                            },
                            imageLoader = imageLoader,
                            topPadding = counterHeight
                        )
                    }

                    is FavoritesUiState.EmptyResult -> {
                        Placeholder(
                            iconRes = R.drawable.placeholder_favorites_empty,
                            message = stringResource(R.string.empty_list)
                        )
                    }

                    is FavoritesUiState.Error -> {
                        Placeholder(
                            iconRes = R.drawable.placeholder_not_found,
                            message = stringResource(R.string.not_found)
                        )
                    }
                }
            }
        }
    }
}
