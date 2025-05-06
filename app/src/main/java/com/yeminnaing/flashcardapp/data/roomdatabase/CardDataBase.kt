package com.yeminnaing.flashcardapp.data.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yeminnaing.flashcardapp.data.roomdatabase.daos.FlashCardDao
import com.yeminnaing.flashcardapp.data.roomdatabase.daos.MarksDao
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.FlashCardEntity
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity

@Database(
    entities = [FlashCardEntity::class,
        MarksEntity::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converter::class)
abstract class CardDataBase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao
    abstract fun marksDao(): MarksDao
}