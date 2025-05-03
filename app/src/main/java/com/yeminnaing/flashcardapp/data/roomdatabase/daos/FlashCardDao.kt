package com.yeminnaing.flashcardapp.data.roomdatabase.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity

@Dao
interface FlashCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashCard(flashCard: FlashCardEntity)

    @Query("SELECT * FROM FlashCard")
    suspend fun getAllCards(): List<FlashCardEntity>

    @Delete
    suspend fun deleteCard(flashCard: FlashCardEntity)



}