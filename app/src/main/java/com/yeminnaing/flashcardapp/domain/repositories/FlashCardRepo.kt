package com.yeminnaing.flashcardapp.domain.repositories

import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity

interface FlashCardRepo {
    suspend fun deleteCard(flashCard: FlashCardEntity)
    suspend fun getAllCards(): List<FlashCardEntity>
    suspend fun getAllMarks(): List<MarksEntity>
    suspend fun insertFlashCard(flashCard: FlashCardEntity)
    suspend fun insertMarks(mark: MarksEntity)
}