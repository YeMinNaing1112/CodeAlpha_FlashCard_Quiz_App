package com.yeminnaing.flashcardapp.data.roomdatabase

import androidx.room.Entity

@Entity(tableName = "Marks")
data class MarksEntity(
    val flashCardId: Int,
    val marks:Int,
)
