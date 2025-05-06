package com.yeminnaing.flashcardapp.presentation.ui.screens.homescreen

import androidx.lifecycle.ViewModel
import com.yeminnaing.flashcardapp.domain.repositories.FlashCardRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenVm @Inject constructor(
    private val flashCardRepo: FlashCardRepo
):ViewModel() {

}