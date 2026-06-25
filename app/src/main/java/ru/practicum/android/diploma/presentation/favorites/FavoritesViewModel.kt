package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

sealed class FavoritesUiState {
    object Loading : FavoritesUiState()
    data class Content(val vacancies: List<Vacancy>, val totalFound: Int) : FavoritesUiState()
    object EmptyResult : FavoritesUiState()
    object Error : FavoritesUiState()
}

class FavoritesViewModel(
    private val vacanciesInteractor: VacanciesInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    fun uploadVacancies() {
        viewModelScope.launch {
            vacanciesInteractor.uploadFavoritesVacancies()
                .onSuccess { flow ->
                    flow.collect {
                        if (it.isEmpty()) _uiState.value = FavoritesUiState.EmptyResult
                        else _uiState.value = FavoritesUiState.Content(vacancies = it, totalFound = it.size)
                    }
                }
                .onFailure { _uiState.value = FavoritesUiState.Error }

        }
    }
}
