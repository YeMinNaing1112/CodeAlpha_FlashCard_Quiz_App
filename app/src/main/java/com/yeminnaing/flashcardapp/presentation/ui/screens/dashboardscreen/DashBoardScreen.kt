package com.yeminnaing.flashcardapp.presentation.ui.screens.dashboardscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.yeminnaing.flashcardapp.R
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity
import com.yeminnaing.flashcardapp.domain.model.AnswerModel
import com.yeminnaing.flashcardapp.domain.model.QuestionModel
import com.yeminnaing.flashcardapp.presentation.navigation.FlashCardScreens
import com.yeminnaing.flashcardapp.presentation.ui.screens.homescreen.MyBottomNavigationBar

@Composable
fun DashBoardScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val viewModel: DashBoardVm = hiltViewModel()
    val states by viewModel.dashBoardStates.collectAsState()
    DashBoardScreenDesign(navHostController = navHostController, states = states)
}

@Composable
fun DashBoardScreenDesign(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    states: DashBoardStates,
) {
    var selectedIndex by remember { mutableIntStateOf(1) }
    Scaffold(
        bottomBar = {
            MyBottomNavigationBar(
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it },
                navigate = {
                    navHostController.navigate(FlashCardScreens.HomeScreen) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            inclusive = true
                            saveState = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    ) {

        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.15f)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(colorResource(R.color.primary_color))

            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .padding(start = 24.dp), verticalArrangement = Arrangement.Center
                ) {
                    Text("Welcome to", color = Color.White, fontSize = 24.sp)
                    Spacer(modifier = modifier.height(16.dp))
                    Text(
                        "DashBoard",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(start = 24.dp)
                    )
                }
            }
            when (states) {
                is DashBoardStates.Empty -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        ShowMarksWithBox(yourMark = 0, totalMark = 0)
                    }
                }

                is DashBoardStates.Error -> {
                    Box(
                        modifier = modifier.fillMaxSize()
                    ) {
                        Text(
                            "Error",
                            color = colorResource(R.color.primary_color),
                            fontSize = 24.sp,
                            modifier = modifier.align(
                                Alignment.Center
                            )
                        )
                    }
                }

                is DashBoardStates.Loading -> {
                    Box(
                        modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is DashBoardStates.Success -> {
                    ShowMarksWithBox(
                        yourMark = states.marks.sumOf { it.marks },
                        totalMark = states.flashCards.sumOf { it.question.size })
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(end = 16.dp)
                    ) {
                        items(states.flashCards) { flashCard ->
                            val mark = states.marks.find { it.flashCardId == flashCard.id }
                            mark?.let { data ->
                                MarksCardDesign(
                                    flashCard = flashCard,
                                    mark = data
                                )
                            }

                        }
                    }
                }
            }


        }
    }


}

@Composable
fun ShowMarksWithBox(modifier: Modifier = Modifier, yourMark: Int, totalMark: Int) {
    Row(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()

    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .height(100.dp)
                .padding(start = 16.dp, end = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    colorResource(R.color.primary_color)
                        .copy(alpha = 0.3f)
                )
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    "Your Marks",
                    color = colorResource(R.color.primary_color),
                    fontSize = 16.sp
                )
                Text(
                    yourMark.toString(),
                    color = colorResource(R.color.primary_color),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Box(
            modifier = modifier
                .weight(1f)
                .height(100.dp)
                .padding(start = 8.dp, end = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Color.LightGray
                        .copy(alpha = 0.3f)
                )
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {
                Text("Total Marks", color = Color.Black, fontSize = 16.sp)
                Text(
                    totalMark.toString(),
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
}

@Composable
fun MarksCardDesign(modifier: Modifier = Modifier, flashCard: FlashCardEntity, mark: MarksEntity) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .height(100.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        val totalMark = flashCard.question.count()
        val yourMark = mark.marks
        Box(modifier = modifier.fillMaxSize()) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .align(
                        Alignment.CenterStart
                    )
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = flashCard.title, color = Color.Black, fontSize = 20.sp)
                Text(text = "$yourMark / $totalMark ", color = Color.Black, fontSize = 20.sp)
            }

        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun DashboardScreenPrev() {
    DashBoardScreenDesign(
        navHostController = NavHostController(context = LocalContext.current),
        states = DashBoardStates.Empty
    )
}

@Preview
@Composable
private fun MarkCardDesignPrev() {
    MarksCardDesign(
        flashCard = FlashCardEntity(
            id = 1,
            title = "Mathematic",
            question = listOf(
                QuestionModel(
                    questionText = "1+1",
                    answerModels = listOf(
                        AnswerModel(
                            text = "3",
                            isCorrect = false
                        ), AnswerModel(
                            text = "2",
                            isCorrect = true
                        ), AnswerModel(
                            text = "4",
                            isCorrect = false
                        )

                    )
                ),
                QuestionModel(
                    questionText = "1+1",
                    answerModels = listOf(
                        AnswerModel(
                            text = "3",
                            isCorrect = false
                        ), AnswerModel(
                            text = "2",
                            isCorrect = true
                        ), AnswerModel(
                            text = "4",
                            isCorrect = false
                        )

                    )
                ),
                QuestionModel(
                    questionText = "1+1",
                    answerModels = listOf(
                        AnswerModel(
                            text = "3",
                            isCorrect = false
                        ), AnswerModel(
                            text = "2",
                            isCorrect = true
                        ), AnswerModel(
                            text = "4",
                            isCorrect = false
                        )

                    )
                )
            )
        ), mark = MarksEntity(
            flashCardId = 3,
            marks = 4
        )
    )

}