package com.yeminnaing.flashcardapp.presentation.ui.screens.flashcardscreen

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.yeminnaing.flashcardapp.R
import com.yeminnaing.flashcardapp.domain.model.AnswerModel
import com.yeminnaing.flashcardapp.domain.model.QuestionModel
import com.yeminnaing.flashcardapp.presentation.dammyData

@Composable
fun FlashCardScreen(modifier: Modifier = Modifier) {

}

@Composable
fun FlashCardScreenDesign(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
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
                    "Title Name",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(start = 24.dp)
                )

            }

        }

        var currentQuestionIndex by remember { mutableIntStateOf(0) }
        val currentQuestion = dammyData[1].question[currentQuestionIndex]


        AnimatedContent(
            targetState = currentQuestionIndex,
            transitionSpec = {
                slideInHorizontally { fullWidth -> fullWidth } + fadeIn() togetherWith
                        slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
            },
            label = "QuestionAnimation"
        ) { index ->
            val question = dammyData[1].question[index]
            QuestionCardDesign(questionModel = question)
        }



        Button(
            onClick = {
                if (currentQuestionIndex < dammyData[1].question.lastIndex) {
                    currentQuestionIndex++
                }
            },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.primary_color))
        ) {
            Text("NextQuestion")
        }
    }

}

@Composable
fun QuestionCardDesign(modifier: Modifier = Modifier, questionModel: QuestionModel) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = questionModel.questionText, color = Color.Black, fontSize = 24.sp)
            var selectedIndex by remember { mutableStateOf<Int?>(null) }
            var isSubmitted by remember { mutableStateOf(false) }
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
                onClick = { isSubmitted = true },
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
        )
    )
}

@Preview(showSystemUi = true)
@Composable
private fun FlashCardScreenDesignPrev() {
    FlashCardScreenDesign()
}