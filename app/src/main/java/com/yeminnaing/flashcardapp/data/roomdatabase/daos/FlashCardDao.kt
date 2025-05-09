package com.yeminnaing.flashcardapp.data.roomdatabase.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashCard(flashCard: FlashCardEntity)

    @Query("SELECT * FROM FlashCard")
     fun getAllCards(): Flow<List<FlashCardEntity>>

    @Delete
    suspend fun deleteCard(flashCard: FlashCardEntity)

    @Query("SELECT * FROM FLASHCARD where id=:id")
    suspend fun getCardById(id: Int): FlashCardEntity


}