package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor

class VacancyViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: VacanciesInteractor
): ViewModel() {

    private val vacancyId: String = checkNotNull(savedStateHandle["vacancyId"])
    private val _state = MutableStateFlow<VacancyState>(VacancyState.Loading)
    val state: StateFlow<VacancyState> = _state

    init {
        loadData()
    }


    fun loadData() {
        viewModelScope.launch {
           interactor.getVacancyDetail(vacancyId).fold(
               onSuccess = {
                   _state.value = VacancyState.Content(it)
               },
               onFailure = {
                   _state.value = VacancyState.Error
               }
           )
        }
    }

    fun onButtonFavoriteClicked() {

    }

    fun onButtonShareClicked() {

    }

    fun onEmailClicked(email: String) {

    }

    fun onPhoneNumberClicked(phone: String) {

    }
}
