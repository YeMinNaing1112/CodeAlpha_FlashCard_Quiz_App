package com.yeminnaing.flashcardapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.yeminnaing.flashcardapp.presentation.ui.screens.answerscreen.AnswerScreen
import com.yeminnaing.flashcardapp.presentation.ui.screens.dashboardscreen.DashBoardScreen
import com.yeminnaing.flashcardapp.presentation.ui.screens.flashcardscreen.FlashCardScreen
import com.yeminnaing.flashcardapp.presentation.ui.screens.homescreen.HomeScreen

@Composable
fun FlashCardScreensNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = FlashCardScreens.HomeScreen) {
        composable<FlashCardScreens.HomeScreen> {
            HomeScreen(navHostController = navController)
        }
        composable<FlashCardScreens.DashBoardScreen> {
            DashBoardScreen(navHostController = navController)
        }
        composable<FlashCardScreens.FlashCardScreen> { backStackEntry->
           val card :FlashCardScreens.FlashCardScreen = backStackEntry.toRoute()
            FlashCardScreen(id = card.id, navHostController = navController)
        }
        composable<FlashCardScreens.AnswerScreen> {
            AnswerScreen(navHostController = navController)
        }
    }
}