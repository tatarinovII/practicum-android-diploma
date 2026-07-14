package ru.practicum.android.diploma.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.FilterEventBus
import ru.practicum.android.diploma.util.debounce
import java.io.IOException

sealed class SearchScreenState {
    object Initial : SearchScreenState()
    object Loading : SearchScreenState()
    data class Content(val vacancies: List<Vacancy>, val totalFound: Int) : SearchScreenState()
    object EmptyResult : SearchScreenState()
    sealed class Error : SearchScreenState() {
        object NoInternet : Error()
        object ServerError : Error()
    }
}

data class SearchUiState(
    val searchQuery: String = "",
    val screenState: SearchScreenState = SearchScreenState.Initial,
    val isLoadingMore: Boolean = false,
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val totalFound: Int = 0
)

class SearchViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor,
    private val filterEventBus: FilterEventBus,
    private val searchInteractor: VacanciesInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    private val _isFilterActive = MutableStateFlow(false)
    val isFilterActive: StateFlow<Boolean> = _isFilterActive.asStateFlow()

    init {
        viewModelScope.launch {
            filterEventBus.filterApplied.collect {
                val query = _uiState.value.searchQuery
                if (query.isNotEmpty()) {
                    performSearch(query, resetPaging = true)
                }
            }
        }
    }

    private val searchDebounce = debounce<String>(
        delayMillis = 2000L,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { query ->
        performSearch(query, resetPaging = true)
    }

    fun refreshFilterState() {
        viewModelScope.launch {
            val settings = filterSettingsInteractor.getFilterSettings()
            val active = settings.countryId != null ||
                settings.regionId != null ||
                settings.industryId != null ||
                settings.salary != null ||
                settings.onlyWithSalary
            _isFilterActive.value = active
        }
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isNotEmpty()) {
            searchDebounce(query)
        } else {
            _uiState.update { it.copy(screenState = SearchScreenState.Initial, totalFound = 0) }
        }
    }

    fun clearQuery() {
        _uiState.update { SearchUiState() }
    }

    fun loadNextPage() {
        val state = _uiState.value
        if (state.isLoadingMore || state.currentPage >= state.totalPages - 1) {
            return
        }
        val query = state.searchQuery
        if (query.isNotEmpty()) {
            performSearch(query, resetPaging = false)
        }
    }

    private fun performSearch(query: String, resetPaging: Boolean) {
        viewModelScope.launch {
            val settings = filterSettingsInteractor.getFilterSettings()
            val areaParam = settings.regionId ?: settings.countryId
            if (resetPaging) {
                _uiState.update {
                    it.copy(
                        screenState = SearchScreenState.Loading,
                        isLoadingMore = false,
                        currentPage = 0
                    )
                }
            } else {
                _uiState.update { it.copy(isLoadingMore = true) }
            }
            val result = searchInteractor.searchVacancies(
                query = query,
                page = if (resetPaging) 0 else _uiState.value.currentPage + 1,
                areaId = areaParam,
                industryId = settings.industryId,
                salary = settings.salary,
                onlyWithSalary = settings.onlyWithSalary
            )
            handleSearchResult(result, resetPaging)
        }
    }

    private fun handleSearchResult(result: Result<SearchResult>, resetPaging: Boolean) {
        _uiState.update { state ->
            val newState = state.copy(isLoadingMore = false)
            result.fold(
                onSuccess = { searchResult -> handleSuccess(searchResult, state, resetPaging, newState) },
                onFailure = { exception -> handleFailure(exception, newState) }
            )
        }
    }

    private fun handleSuccess(
        searchResult: SearchResult,
        state: SearchUiState,
        resetPaging: Boolean,
        newState: SearchUiState
    ): SearchUiState {
        val newVacancies = if (resetPaging) {
            searchResult.vacancies
        } else {
            state.screenState.let {
                if (it is SearchScreenState.Content) {
                    it.vacancies + searchResult.vacancies
                } else {
                    searchResult.vacancies
                }
            }
        }
        return newState.copy(
            screenState = if (searchResult.vacancies.isEmpty() && resetPaging) {
                SearchScreenState.EmptyResult
            } else if (searchResult.vacancies.isEmpty()) {
                if (state.screenState is SearchScreenState.Content) {
                    SearchScreenState.Content(
                        vacancies = state.screenState.vacancies,
                        totalFound = state.screenState.totalFound
                    )
                } else {
                    SearchScreenState.EmptyResult
                }
            } else {
                SearchScreenState.Content(
                    vacancies = newVacancies,
                    totalFound = searchResult.totalFound
                )
            },
            currentPage = searchResult.currentPage,
            totalPages = searchResult.totalPages,
            totalFound = searchResult.totalFound,
            isLoadingMore = false
        )
    }

    private fun handleFailure(exception: Throwable, newState: SearchUiState): SearchUiState {
        val errorState = when (exception) {
            is IOException -> SearchScreenState.Error.NoInternet
            else -> SearchScreenState.Error.ServerError
        }
        return newState.copy(
            screenState = errorState,
            totalFound = 0
        )
    }
}
