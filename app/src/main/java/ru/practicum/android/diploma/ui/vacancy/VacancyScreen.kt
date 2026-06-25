package ru.practicum.android.diploma.ui.vacancy

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.jsoup.Jsoup
import org.koin.compose.viewmodel.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.vacancy.VacancyState
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.ui.theme.MyAppTheme
import ru.practicum.android.diploma.util.formatSalary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyScreen(
    navController: NavController,
    viewModel: VacancyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    MyAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_vacancy),
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
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.onButtonShareClicked() },
                        modifier = Modifier.padding(
                                vertical = 8.dp,
                                horizontal = 4.dp
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_share_24px),
                            contentDescription = stringResource(R.string.share),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = { viewModel.onButtonFavoriteClicked() },
                        modifier = Modifier.padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                end = 8.dp
                            )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_favorite_24px),
                            contentDescription = stringResource(R.string.favorite),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when(state) {
                    is VacancyState.Loading -> {
                        ShowLoading()
                    }
                    is VacancyState.Content -> {
                        ShowContent((
                            state as VacancyState.Content).vacancyDetail,
                            vacancyViewModel = viewModel
                        )
                    }
                    is VacancyState.NotFound -> {
                        ShowNotFoundPlaceHolder()
                    }
                    else -> {
                        ShowErrorPlaceHolder()
                    }
                }
            }
        }
    }
}

@Composable
fun ShowLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(44.dp),
            color = Color.Blue
        )
    }
}

@Composable
fun ShowContent(
    vacancyDetail: VacancyDetail,
    vacancyViewModel: VacancyViewModel
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
      item { VacancyDetail(
          vacancyDetail,
          vacancyViewModel
      )}
    }
}

@Composable
fun ShowNotFoundPlaceHolder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.vacancy_not_found),
            contentDescription = stringResource(R.string.vacancy_not_found)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.vacancy_not_found),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
@Composable
fun ShowErrorPlaceHolder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.servers_error_vacancy),
            contentDescription = stringResource(R.string.servers_error_vacancy)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.servers_error_vacancy),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun VacancyDetail(
    vacancyDetail: VacancyDetail,
    viewModel: VacancyViewModel
) {
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = vacancyDetail.name,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyLarge
    )

    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = vacancyDetail.salary?.formatSalary() ?: stringResource(R.string.salary_not_specified),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleLarge
    )

    BoxTask(vacancyDetail)

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = stringResource(R.string.experience),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(4.dp))

    vacancyDetail.experience?.name?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "${vacancyDetail.employment?.name}, ${vacancyDetail.schedule?.name}",
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleSmall
    )

    Spacer(modifier = Modifier.height(32.dp))

    DescriptionVacancy(vacancyDetail.description)

    Spacer(modifier = Modifier.height(24.dp))

    if (vacancyDetail.skills?.isNotEmpty() == true) {
        Text(
            text = stringResource(R.string.skills),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        vacancyDetail.skills.forEach { skill ->
            Text(
                text = "• $skill",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    if (vacancyDetail.contacts?.email?.isNotEmpty() == true || vacancyDetail.contacts?.phones?.isNotEmpty() == true) {
        Text(
            text = stringResource(R.string.contacts),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        if (vacancyDetail.contacts.name?.isNotEmpty() == true) {
            Text(
                text = vacancyDetail.contacts.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
        }

        if (vacancyDetail.contacts.email?.isNotEmpty() == true) {
            Text(
                modifier = Modifier.clickable {viewModel.onEmailClicked(vacancyDetail.contacts.email)},
                text = vacancyDetail.contacts.email,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
        }

        if (vacancyDetail.contacts.phones?.isNotEmpty() == true) {
            vacancyDetail.contacts.phones.forEach { phone ->
                Text(
                    modifier = Modifier.clickable {viewModel.onPhoneNumberClicked(phone.formatted.toString())},
                    text = phone.formatted.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun BoxTask(vacancyDetail: VacancyDetail) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
    ) {
        VacancyEasyCard(vacancyDetail)
    }
}

@Composable
fun VacancyEasyCard(vacancyDetail: VacancyDetail) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        AsyncImage(
            model = vacancyDetail.employer?.logo,
            contentDescription = stringResource(R.string.company_logo),
            modifier = Modifier
                .size(48.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(R.drawable.placeholder_32px),
            error = painterResource(R.drawable.placeholder_32px),
            fallback = painterResource(R.drawable.placeholder_32px),
            onError = { state ->
                Log.e("VacancyEasyCard", "Error loading ${vacancyDetail.employer?.logo}: ${state.result.throwable}")
            }
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            vacancyDetail.employer?.name?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            vacancyDetail.area?.name?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun DescriptionVacancy(description: String?) {
    val blocks = remember(description) { parseHtmlToBlocks(description) }

    Column{
        blocks.forEach { block ->
            when(block) {
                is DescriptionBlocksFromHtml.Header -> {
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = block.text,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                is DescriptionBlocksFromHtml.SmallHeader -> {
                    Text(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            bottom = 4.dp
                        ),
                        text = block.text,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                is DescriptionBlocksFromHtml.ListItem -> {
                    block.items.forEach { item ->
                        Text(
                            text = "• $item",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                is DescriptionBlocksFromHtml.Text -> {
                    Text(
                        text = block.content,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

fun parseHtmlToBlocks(html: String?): List<DescriptionBlocksFromHtml> {
    if (html?.isNotEmpty() != true) {
        return emptyList()
    }
    val document = Jsoup.parse(html)
    val blocks = mutableListOf<DescriptionBlocksFromHtml>()
    val elements = document.select("h2, h3, p, ul")
    elements.forEach { element ->
        when (element.tagName()) {
            "h2" -> {
                val level = element.tagName().substring(1).toInt()
                blocks.add(DescriptionBlocksFromHtml.Header(element.text(), level))
            }

            "h3" -> {
                val level = element.tagName().substring(1).toInt()
                blocks.add(DescriptionBlocksFromHtml.SmallHeader(element.text(), level))
            }

            "p" -> {
                blocks.add(DescriptionBlocksFromHtml.Text(element.text()))
            }

            "ul" -> {
                val items = element.select("li").map { it.text() }
                blocks.add(DescriptionBlocksFromHtml.ListItem(items))
            }
        }
    }
    return blocks
}
