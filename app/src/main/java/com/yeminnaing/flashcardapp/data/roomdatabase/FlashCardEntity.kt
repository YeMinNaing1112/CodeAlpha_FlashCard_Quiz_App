package com.yeminnaing.flashcardapp.data.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yeminnaing.flashcardapp.domain.model.QuestionModel

@Entity(tableName = "FlashCard")
data class FlashCardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title:String,
    val question: List<QuestionModel>,
)
