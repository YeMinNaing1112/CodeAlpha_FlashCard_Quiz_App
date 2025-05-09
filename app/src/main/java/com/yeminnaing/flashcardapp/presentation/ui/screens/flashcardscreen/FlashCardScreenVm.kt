package com.yeminnaing.flashcardapp.presentation.ui.screens.flashcardscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity
import com.yeminnaing.flashcardapp.domain.repositories.FlashCardRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashCardScreenVm @Inject constructor(
    private val flashCardRepo: FlashCardRepo,
) : ViewModel() {
    private val _flashCardStates =
        MutableStateFlow<FlashCardScreenStates>(FlashCardScreenStates.Empty)
    val flashCardStates = _flashCardStates.asStateFlow()
    fun getFlashCard(id: Int) {
        _flashCardStates.value = FlashCardScreenStates.Loading
        viewModelScope.launch {
            try {
                val card = flashCardRepo.getCardById(id = id)
                _flashCardStates.value = FlashCardScreenStates.Success(card)
            } catch (e: Exception) {
                _flashCardStates.value = FlashCardScreenStates.Error(e.toString())
            }


        }
    }

    fun addMarks(marks: MarksEntity) {
        viewModelScope.launch {
            flashCardRepo.insertMarks(mark = marks)
        }
    }

    sealed interface FlashCardScreenStates {
        data object Empty : FlashCardScreenStates
        data object Loading : FlashCardScreenStates
        data class Success(val card: FlashCardEntity) : FlashCardScreenStates
        data class Error(val error: String) : FlashCardScreenStates
    }
}