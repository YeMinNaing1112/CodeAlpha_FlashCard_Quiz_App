package com.yeminnaing.flashcardapp.presentation.ui.screens.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.domain.repositories.FlashCardRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenVm @Inject constructor(
    private val flashCardRepo: FlashCardRepo,
) : ViewModel() {
    private val _homeScreenStates = MutableStateFlow<HomeScreenStates>(HomeScreenStates.Empty)
    val homeScreenStates = _homeScreenStates.asStateFlow()

    init {
        getFlashCard()
    }

    private fun getFlashCard() {
        _homeScreenStates.value = HomeScreenStates.Loading
        viewModelScope.launch {
            flashCardRepo.getAllCards()
                .catch { e ->
                    _homeScreenStates.value = HomeScreenStates.Error(e.message ?: "Unknown error")
                }
                .collect { cards ->
                    _homeScreenStates.value = HomeScreenStates.Success(flashCards = cards)
                }
        }


    }

    fun deleteCard(card: FlashCardEntity) {
        viewModelScope.launch {
            flashCardRepo.deleteCard(card)
        }
    }

    fun deleteMark(id: Int) {
        viewModelScope.launch {
            flashCardRepo.deleteMark(id)
        }
    }


    sealed interface HomeScreenStates {
        data object Empty : HomeScreenStates
        data object Loading : HomeScreenStates
        data class Success(val flashCards: List<FlashCardEntity>) : HomeScreenStates
        data class Error(val error: String) : HomeScreenStates
    }
}