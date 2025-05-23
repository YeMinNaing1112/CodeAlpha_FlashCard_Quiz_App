package com.yeminnaing.flashcardapp.presentation.ui.screens.answerscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yeminnaing.flashcardapp.R
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.domain.model.AnswerModel
import com.yeminnaing.flashcardapp.domain.model.QuestionModel

@Composable
fun AnswerScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val viewModel: AnswerScreenVm = hiltViewModel()

    AnswerScreenDesign(addFlashCards = { flashCard ->
        viewModel.addFlashCards(flashCard)
    }, popUpTo = {
        navHostController.navigateUp()
    })
}

@Composable
fun AnswerScreenDesign(
    modifier: Modifier = Modifier,
    addFlashCards: (flashCard: FlashCardEntity) -> Unit,
    popUpTo: () -> Unit,
) {
    var flashCard: FlashCardEntity
    val questions = remember { mutableStateListOf<QuestionModel>() }
    val answers = remember { mutableStateListOf<AnswerModel>() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary_color))
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var titleText by remember { mutableStateOf("") }
        var questionText by remember { mutableStateOf("") }
        var dialogFlag by remember { mutableStateOf(false) }

        Text(
            text = "Add Flashcard",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(24.dp)
        )

        Column(modifier = modifier.width(300.dp)) {
            Text(
                text = "Title",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = titleText,
                onValueChange = { titleText = it },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorResource(R.color.primary_color),
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
                placeholder = { Text("TitleName", color = colorResource(R.color.primary_color)) },
                modifier = modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = modifier
                .padding(top = 24.dp)
                .width(300.dp)
        ) {
            Text(
                text = "Question",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = questionText,
                onValueChange = { questionText = it },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorResource(R.color.primary_color),
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
                placeholder = { Text("Question", color = colorResource(R.color.primary_color)) },
                modifier = modifier.fillMaxWidth()
            )
        }


        Column(
            modifier = modifier
                .width(300.dp)
                .padding(top = 24.dp)
        ) {
            Text(
                text = "Answers",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            LazyColumn(
                modifier = modifier.fillMaxWidth()
            ) {
                items(items = answers) { answer ->
                    Box(
                        modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                    ) {
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(answer.text, color = colorResource(R.color.primary_color))
                            Text(
                                answer.isCorrect.toString(),
                                color = colorResource(R.color.primary_color)
                            )
                        }
                    }

                }
            }


        }

        Button(
            onClick = {
                dialogFlag = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary_dark)),
            modifier = Modifier
                .padding(top = 16.dp)
                .width(300.dp)
        ) {
            Text(text = "+ Add More Answer", color = Color.White)
        }

        Button(
            onClick = {
                questions.add(
                    QuestionModel(
                        questionText = questionText,
                        answerModels = answers.toList()
                    )
                )

                questionText = ""
                answers.clear()
            },
            enabled = answers.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary_dark)),
            modifier = Modifier
                .padding(top = 16.dp)
                .width(300.dp)
        ) {
            Text(text = "Save Question", color = Color.White)
        }

        Button(
            onClick = {
                flashCard = FlashCardEntity(
                    title = titleText,
                    question = questions.toList()
                )
                addFlashCards(flashCard)
                popUpTo()
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary_dark)),
            modifier = Modifier
                .padding(32.dp)
                .width(300.dp),
            enabled = titleText.isNotBlank() && questions.isNotEmpty()

        ) {
            Text(text = "Save Flashcard", color = Color.White)
        }


        if (dialogFlag) {
            Dialog(
                onDismissRequest = { dialogFlag = false }
            ) {
                AddAnswerDialog(
                    onDismiss = {
                        dialogFlag = it
                    },
                    addAnswer = { answer, isCorrect ->
                        answers.add(AnswerModel(text = answer, isCorrect))
                    }
                )
            }
        }

    }
}

@Composable
fun AddAnswerDialog(
    modifier: Modifier = Modifier,
    onDismiss: (Boolean) -> Unit,
    addAnswer: (answer: String, isCorrect: Boolean) -> Unit,
) {

    var answerText by remember { mutableStateOf("") }
    var isSelected by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .width(300.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(colorResource(R.color.primary_color))
            .padding(16.dp)
    ) {
        Text(
            text = "Answer",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = answerText,
            onValueChange = { answerText = it },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.primary_color),
                cursorColor = Color.Black,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            placeholder = { Text("Question", color = colorResource(R.color.primary_color)) },
            modifier = modifier.fillMaxWidth()
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("Is Correct", color = Color.White)

            RadioButton(onClick = {
                isSelected = !isSelected
            }, selected = isSelected)
        }
        Button(
            onClick = {
                addAnswer(answerText, isSelected)
                onDismiss(false)
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary_dark)),
            modifier = Modifier
                .padding(32.dp)
                .width(300.dp)

        ) {
            Text(text = "Save Answer", color = Color.White)
        }

    }


}

@Preview
@Composable
private fun AnswerScreenDesignPrev() {
    AnswerScreenDesign(addFlashCards = {}, popUpTo = {})
}

@Preview
@Composable
private fun AddAnswerDialogPrev() {
    AddAnswerDialog(onDismiss = {}, addAnswer = { _, _ -> })
}