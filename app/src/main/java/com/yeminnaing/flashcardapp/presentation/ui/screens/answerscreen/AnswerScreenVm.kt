package com.yeminnaing.flashcardapp.presentation.ui.screens.answerscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.domain.repositories.FlashCardRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerScreenVm @Inject constructor(
    private val flashCardRepo: FlashCardRepo,
) : ViewModel() {

    fun addFlashCards(card: FlashCardEntity) {
        viewModelScope.launch {
            flashCardRepo.insertFlashCard(card)
        }
    }
}