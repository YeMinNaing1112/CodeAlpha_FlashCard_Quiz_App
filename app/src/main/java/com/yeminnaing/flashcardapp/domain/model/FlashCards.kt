package com.yeminnaing.flashcardapp.domain.model

data class AnswerModel(
    val text: String,
    val isCorrect: Boolean
)

data class QuestionModel(
    val questionText: String,
    val answerModels: List<AnswerModel>
)