package com.yeminnaing.flashcardapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class FlashCardScreens {
    @Serializable
    data object HomeScreen : FlashCardScreens()

    @Serializable
    data object DashBoardScreen : FlashCardScreens()

    @Serializable
    data class FlashCardScreen(val id : Int) : FlashCardScreens()

    @Serializable
    data object AnswerScreen : FlashCardScreens()
}