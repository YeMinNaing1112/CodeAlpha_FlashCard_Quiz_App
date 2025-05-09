package com.yeminnaing.flashcardapp.presentation.ui.screens.flashcardscreen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yeminnaing.flashcardapp.R
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity
import com.yeminnaing.flashcardapp.domain.model.AnswerModel
import com.yeminnaing.flashcardapp.domain.model.QuestionModel

@Composable
fun FlashCardScreen(modifier: Modifier = Modifier, id: Int, navHostController: NavHostController) {
    val viewModel: FlashCardScreenVm = hiltViewModel()
    val state by viewModel.flashCardStates.collectAsState()
    LaunchedEffect(viewModel) {
        viewModel.getFlashCard(id = id)
    }
    FlashCardScreenDesign(state = state, popUpTo = {
        navHostController.navigateUp()
    }, addMarks = {
        viewModel.addMarks(it)
    })
}

@Composable
fun FlashCardScreenDesign(
    modifier: Modifier = Modifier,
    state: FlashCardScreenVm.FlashCardScreenStates,
    popUpTo: () -> Unit,
    addMarks: (mark: MarksEntity) -> Unit,
) {

    var marks by remember { mutableStateOf(0) }
    var isSubmitted by remember { mutableStateOf(false) }

    when (state) {
        is FlashCardScreenVm.FlashCardScreenStates.Empty -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text(
                    "There is No Cards",
                    color = colorResource(R.color.primary_color),
                    fontSize = 24.sp,
                    modifier = modifier.align(
                        Alignment.Center
                    )
                )
            }
        }

        is FlashCardScreenVm.FlashCardScreenStates.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
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

        is FlashCardScreenVm.FlashCardScreenStates.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                CircularProgressIndicator(
                    modifier = modifier.align(
                        Alignment.Center
                    )
                )
            }
        }

        is FlashCardScreenVm.FlashCardScreenStates.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
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
                        Text(
                            state.card.title,
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = modifier.padding(start = 24.dp)
                        )

                    }

                }

                var currentQuestionIndex by remember { mutableIntStateOf(0) }


                AnimatedContent(
                    targetState = currentQuestionIndex,
                    transitionSpec = {
                        slideInHorizontally { fullWidth -> fullWidth } + fadeIn() togetherWith
                                slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
                    },
                    label = "QuestionAnimation"
                ) { index ->
                    val question = state.card.question[index]
                    QuestionCardDesign(questionModel = question, isSubmit = {
                        isSubmitted = it
                    }, calculateMarks = {
                        marks += it
                    })
                }

                if (currentQuestionIndex == state.card.question.lastIndex) {
                    Button(
                        onClick = {
                            addMarks(
                                MarksEntity(
                                    flashCardId = state.card.id,
                                    marks = marks
                                )
                            )
                            popUpTo()
                        },
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.primary_color))
                    ) {
                        Text("Finish", color = Color.White)
                    }
                } else {
                    Button(
                        onClick = {
                            if (currentQuestionIndex < state.card.question.lastIndex) {
                                currentQuestionIndex++
                            }
                        },
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.primary_color)),
                        enabled = isSubmitted
                    ) {

                        Text("NextQuestion", color = Color.White)
                    }
                }


            }
        }
    }


}

@Composable
fun QuestionCardDesign(
    modifier: Modifier = Modifier,
    questionModel: QuestionModel,
    isSubmit: (Boolean) -> Unit,
    calculateMarks: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        var selectedIndex by remember { mutableStateOf<Int?>(null) }

        var isSubmitted by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = questionModel.questionText, color = Color.Black, fontSize = 24.sp)

            LazyColumn {
                itemsIndexed(questionModel.answerModels) { index, answer ->
                    val isSelected = selectedIndex == index
                    val backgroundColor = when {
                        isSubmitted && isSelected && answer.isCorrect -> colorResource(R.color.primary_color)
                        isSubmitted && isSelected && !answer.isCorrect -> Color.Red
                        isSelected -> Color.Gray
                        else -> Color.LightGray
                    }

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(backgroundColor.copy(alpha = 0.2f))
                            .border(
                                width = 2.dp,
                                color = backgroundColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable(
                                enabled = !isSubmitted
                            ) { selectedIndex = index }
                            .padding(16.dp)
                    ) {
                        Text(
                            text = answer.text,
                            modifier = modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }
                }
            }
            Button(
                onClick = {
                    isSubmitted = true
                    isSubmit(true)

                    val selectedAnswer = selectedIndex?.let {
                        questionModel.answerModels.getOrNull(
                            it
                        )
                    }
                    if (selectedAnswer != null && selectedAnswer.isCorrect) {
                        calculateMarks(1)
                    }

                },
                enabled = selectedIndex != null && !isSubmitted,
                colors = ButtonDefaults.buttonColors(colorResource(R.color.primary_color))
            ) {
                Text("Confirm")
            }
        }
    }
}

@Preview
@Composable
private fun QuestionCardDesignPrev() {

    QuestionCardDesign(
        questionModel = QuestionModel(
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
        ), isSubmit = {},
        calculateMarks = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun FlashCardScreenDesignPrev() {
    FlashCardScreenDesign(state = FlashCardScreenVm.FlashCardScreenStates.Success(
        card = FlashCardEntity(
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
        )
    ), popUpTo = {},
        addMarks = {})
}