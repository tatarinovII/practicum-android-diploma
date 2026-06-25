package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: VacanciesInteractor
): ViewModel() {

    private val vacancyId: String = checkNotNull(savedStateHandle["vacancyId"])
    private val _state = MutableStateFlow<VacancyState>(VacancyState.Loading)
    val state: StateFlow<VacancyState> = _state

    private val vacancyUiStateLiveData = MutableLiveData<VacancyUiState>()
    fun observeVacancyUiState(): LiveData<VacancyUiState> = vacancyUiStateLiveData

    fun getVacancyDetail() {

    }

    fun shareVacancy() {

    }

    fun addToFavorites() {

    }

    private lateinit var vacancyDetail: VacancyDetail

    init {
        loadData()
    }


    fun loadData() {
        viewModelScope.launch {
            val isFavorite = interactor.isFavorite(vacancyId).fold(
                onSuccess = { it },
                onFailure = { false },
            )
           interactor.getVacancyDetail(vacancyId).fold(
               onSuccess = {
                   vacancyDetail = it
                   _state.value = VacancyState.Content(vacancyDetail, isFavorite)
               },
               onFailure = {
                   _state.value = VacancyState.Error
               }
           )
        }
    }

    fun onButtonFavoriteClicked() {
        viewModelScope.launch {
            val isFavorite = interactor.isFavorite(vacancyId).fold(onSuccess = { it }, onFailure = { false })
            if (isFavorite) {
                interactor.deleteFromFavorite(vacancyId)
            } else interactor.addToFavorite(vacancyDetail)
        }
    }

    fun onButtonShareClicked() {
        interactor.shareVacancy(vacancyDetail.url.toString())
    }

    fun onEmailClicked(email: String) {
        interactor.sendEmail(email)
    }

    fun onPhoneNumberClicked(phone: String) {
        interactor.callNumber(phone)
    }

    private val vacancyUiStateLiveData = MutableLiveData<VacancyUiState>()
    fun observeVacancyUiState(): LiveData<VacancyUiState> = vacancyUiStateLiveData
}
