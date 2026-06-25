package ru.practicum.android.diploma.ui.vacancy

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.jsoup.Jsoup
import ru.practicum.android.diploma.ui.theme.MyAppTheme
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.vacancy.VacancyUiState
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.ui.navigation.Route
import ru.practicum.android.diploma.util.formatSalary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyScreen(
    navController: NavController,
    viewModel: VacancyViewModel,
    vacancyId: String
) {

    val vacancyUiState by viewModel.observeVacancyUiState().observeAsState(initial = VacancyUiState.Loading)

    // Тестовая строка html
    val html: String? = "<h2>Описание вакансии</h2><p>Ищем DevOps-инженера в инфраструктурную команду, которая отвечает за CI/CD, стабильность сервисов и среду разработки.</p><p>Подойдёт специалисту, который любит автоматизировать рутину и понимает эксплуатацию систем в продакшене.</p><section><h3>Обязанности</h3><ul><li>Развивать пайплайны CI/CD и стандарты поставки приложений.</li><li>Поддерживать Kubernetes-кластеры и инфраструктурные сервисы.</li><li>Настраивать мониторинг, алертинг и логирование.</li></ul></section><section><h3>Требования</h3><ul><li>Опыт работы с Docker и Kubernetes.</li><li>Понимание Linux, сетевого взаимодействия и принципов наблюдаемости.</li><li>Практический опыт с GitLab CI, Ansible или Terraform.</li></ul></section><section><h3>Условия</h3><ul><li>Сильная инженерная культура и техническая автономия.</li><li>Задачи на развитие платформы, а не только на ручную поддержку.</li><li>Гибкий формат работы и компенсация профильного обучения.</li></ul></section>"

    // Тестовая вакансия
    val vacancyDetail: VacancyDetail = VacancyDetail(
        id = "001cef68-027f-36b2-b7e0-b4e10a2d831f",
        name = "DevOps-инженер",
        description = html,
        salary = Salary(
            from = null,
            to = 70000,
            currency = Currency.UAH
        ),
        address = Address(
            city = "Екатеринбург",
            street = "Ленина",
            building = "4",
            raw = "Екатеринбург, Ленина, 4"
        ),
        experience = Experience(
            name = "От 1 года до 3 лет"
        ),
        schedule = Schedule(
            name = "Удаленная работа"
        ),
        employment = Employment(
            name = "Полная занятость"
        ),
        contacts = Contacts(
            name = "Петров Петр Петрович",
            email = "",
            phones = listOf(
                Phone(
                    comment = null,
                    formatted = "+7 (999) 234-56-78"
                ),
                Phone(
                    comment = null,
                    formatted = "+7 (999) 876-54-32"
                )
            )
        ),
        employer = Employer(
            name = "Adobe",
            logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Adobe_Corporate_logo.svg/500px-Adobe_Corporate_logo.svg.png"
        ),
        area = Area(
            name = "Екатеринбург"
        ),
        skills = listOf(
            "Docker",
            "Kubernetes",
            "Linux",
            "GitLab CI",
            "Ansible"
        ),
        url = "cek6h0n4ffe7ur.cluster-czz5s0kz4scl.eu-west-1.rds.amazonaws.com/vacancies/001cef68-027f-36b2-b7e0-b4e10a2d831f",
        industry = Industry(
            name = "Информационные технологии, системная интеграция, интернет"
        )
    )

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
                        onClick = { navController.navigate(Route.VACANCY.name) },
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
                        onClick = { viewModel.shareVacancy() },
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
                        onClick = { viewModel.addToFavorites() },
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
            ShowContent(vacancyDetail)

            Box(modifier = Modifier.fillMaxSize()) {
                when(val state = vacancyUiState) {
                    is VacancyUiState.Loading -> {
                        ShowLoading()
                    }
                    is VacancyUiState.Content -> {
                        ShowContent(vacancyDetail)
                    }
                    is VacancyUiState.NotFound -> {
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
fun ShowContent(vacancyDetail: VacancyDetail) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
      item { VacancyDetail(vacancyDetail)}
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
fun VacancyDetail(vacancyDetail: VacancyDetail) {
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
fun DescriptionVacancy(html: String?) {
    val blocks = remember(html) { parseHtmlToBlocks(html) }

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

@Preview
@Composable
fun PreviewVacancyDetail() {
    //VacancyScreen("1")
}
