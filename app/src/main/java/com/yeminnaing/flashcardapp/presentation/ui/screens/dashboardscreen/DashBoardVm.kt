package com.yeminnaing.flashcardapp.presentation.ui.screens.dashboardscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity
import com.yeminnaing.flashcardapp.domain.repositories.FlashCardRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardVm @Inject constructor(
    private val flashCardRepo: FlashCardRepo,
) : ViewModel() {

    private val _dashBoardState = MutableStateFlow<DashBoardStates>(DashBoardStates.Empty)
    val dashBoardStates = _dashBoardState.asStateFlow()

    init {
        getFlashCardAndMarks()
    }

    private fun getFlashCardAndMarks() {
        _dashBoardState.value = DashBoardStates.Loading

        viewModelScope.launch {
            try {
                val marks = flashCardRepo.getAllMarks()
                flashCardRepo.getAllCards()
                    .catch { e ->
                        _dashBoardState.value = DashBoardStates.Error(e.message ?: "Unknown Error")
                    }
                    .collect { cards ->
                        _dashBoardState.value = DashBoardStates.Success(cards, marks)
                    }

            } catch (e: Exception) {
                _dashBoardState.value = DashBoardStates.Error(e.message ?: "Unknown Error")
            }
        }
    }
    }


    sealed interface DashBoardStates {
        data object Empty : DashBoardStates
        data object Loading : DashBoardStates
        data class Success(val flashCards: List<FlashCardEntity>, val marks: List<MarksEntity>) :
            DashBoardStates

        data class Error(val error: String) : DashBoardStates
    }