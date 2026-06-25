package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyViewModel: ViewModel() {

    private val vacancyUiStateLiveData = MutableLiveData<VacancyUiState>()
    fun observeVacancyUiState(): LiveData<VacancyUiState> = vacancyUiStateLiveData

    fun getVacancyDetail() {

    }

    fun shareVacancy() {

    }

    fun addToFavorites() {

    }
}
