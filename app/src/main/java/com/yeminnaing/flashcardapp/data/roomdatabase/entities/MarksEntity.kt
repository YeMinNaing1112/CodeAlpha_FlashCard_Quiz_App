package com.yeminnaing.flashcardapp.data.roomdatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Marks")
data class MarksEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val flashCardId: Int,
    val marks:Int,
)
