package com.yeminnaing.flashcardapp.data.roomdatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Marks")
data class MarksEntity(
    @PrimaryKey
    val flashCardId: Int,
    val marks:Int,
)
