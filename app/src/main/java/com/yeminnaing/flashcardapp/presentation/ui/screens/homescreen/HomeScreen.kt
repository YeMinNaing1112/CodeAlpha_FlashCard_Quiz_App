package com.yeminnaing.flashcardapp.presentation.ui.screens.homescreen

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yeminnaing.flashcardapp.R
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.domain.model.AnswerModel
import com.yeminnaing.flashcardapp.domain.model.QuestionModel
import com.yeminnaing.flashcardapp.presentation.dammyData

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

}

@Composable
fun HomeScreenDesign(modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colorResource(R.color.primary_color),
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }, bottomBar = {
            MyBottomNavigationBar(
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
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
                    .fillMaxSize(0.25F)
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
                        "FlashCardApp",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(start = 24.dp)
                    )
                }
            }
            LazyColumn(
                modifier = modifier.fillMaxSize().padding(end = 16.dp)
            ) {
                items(dammyData) { flashCard ->
                    CardDesign(flashCard = flashCard)
                }
            }
        }

    }
}


@Composable
fun CardDesign(modifier: Modifier = Modifier, flashCard: FlashCardEntity) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp,top=16.dp, end = 16.dp)
            .height(100.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
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
                Image(imageVector = Icons.Default.Delete, contentDescription = "delete")
            }

        }

    }
}


data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
)

@Composable
fun MyBottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("List", Icons.Default.List),
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedIndex,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (index == selectedIndex)
                            Color.White
                        else
                            Color.Black
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorResource(R.color.primary_color)
                )

            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenDesignPrev() {
    HomeScreenDesign()
}

@Preview(showBackground = true)
@Composable
private fun CardDesignPrev() {
    CardDesign(
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
        )
    )
}