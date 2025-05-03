package com.yeminnaing.flashcardapp.data.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashCard(flashCard: FlashCardEntity)

    @Query("SELECT * FROM FlashCard")
    suspend fun getAllCards(): List<FlashCardEntity>

    @Delete
    suspend fun deleteCard(flashCard: FlashCardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarks(marks: MarksEntity)

    @Query("SELECT * FROM Marks")
    suspend fun getAllMarks(): List<MarksEntity>

}