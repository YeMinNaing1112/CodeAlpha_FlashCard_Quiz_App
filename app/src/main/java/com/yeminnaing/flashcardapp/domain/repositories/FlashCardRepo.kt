package com.yeminnaing.flashcardapp.domain.repositories

import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity
import kotlinx.coroutines.flow.Flow

interface FlashCardRepo {
    suspend fun deleteCard(flashCard: FlashCardEntity)
    fun getAllCards(): Flow<List<FlashCardEntity>>
    suspend fun getCardById(id: Int): FlashCardEntity
    suspend fun getAllMarks(): List<MarksEntity>
    suspend fun insertFlashCard(flashCard: FlashCardEntity)
    suspend fun insertMarks(mark: MarksEntity)
    suspend fun deleteMark(id:Int)
}