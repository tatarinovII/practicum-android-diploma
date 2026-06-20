package ru.practicum.android.diploma.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

data class SearchUiState(
    val searchQuery: String = "",
    val vacancies: List<Vacancy> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val totalFound: Int = 0,
    val error: String? = null
)

class SearchViewModel(
    private val searchInteractor: VacanciesInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val searchDebounce = debounce<String>(
        delayMillis = 2000L,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { query ->
        performSearch(query, resetPaging = true)
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isNotEmpty()) {
            searchDebounce(query)
        } else {
            clearQuery()
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
            val currentState = _uiState.value
            val page = if (resetPaging) 0 else currentState.currentPage + 1

            _uiState.update {
                it.copy(
                    isLoading = resetPaging,
                    isLoadingMore = !resetPaging,
                    error = null
                )
            }

            val result = searchInteractor(
                text = query,
                page = page
            )

            _uiState.update { state ->
                val newState = state.copy(
                    isLoading = false,
                    isLoadingMore = false
                )
                result.fold(
                    onSuccess = { searchResult ->
                        val newVacancies = if (resetPaging) {
                            searchResult.vacancies
                        } else {
                            state.vacancies + searchResult.vacancies
                        }
                        newState.copy(
                            vacancies = newVacancies,
                            currentPage = searchResult.currentPage,
                            totalPages = searchResult.totalPages,
                            totalFound = searchResult.totalFound,
                            error = null
                        )
                    },
                    onFailure = { exception ->
                        newState.copy(
                            error = exception.message ?: "Произошла ошибка",
                        )
                    }
                )
            }
        }
    }
}
