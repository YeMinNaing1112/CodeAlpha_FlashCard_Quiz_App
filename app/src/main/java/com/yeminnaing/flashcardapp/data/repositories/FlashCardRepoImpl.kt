package com.yeminnaing.flashcardapp.data.repositories

import com.yeminnaing.flashcardapp.data.roomdatabase.daos.FlashCardDao
import com.yeminnaing.flashcardapp.data.roomdatabase.daos.MarksDao
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity
import com.yeminnaing.flashcardapp.domain.repositories.FlashCardRepo
import javax.inject.Inject

class FlashCardRepoImpl @Inject constructor(
    private val flashCardDao : FlashCardDao,
    private val marksDao : MarksDao
):FlashCardRepo{
    override suspend fun deleteCard(flashCard: FlashCardEntity) {
        flashCardDao.deleteCard(flashCard)
    }

    override suspend fun getAllCards(): List<FlashCardEntity> {
       return  flashCardDao.getAllCards()
    }

    override suspend fun getAllMarks(): List<MarksEntity> {
       return marksDao.getAllMarks()
    }

    override suspend fun insertFlashCard(flashCard: FlashCardEntity) {
        return flashCardDao.insertFlashCard(flashCard)
    }

    override suspend fun insertMarks(mark: MarksEntity) {
        return marksDao.insertMarks(mark)
    }


}