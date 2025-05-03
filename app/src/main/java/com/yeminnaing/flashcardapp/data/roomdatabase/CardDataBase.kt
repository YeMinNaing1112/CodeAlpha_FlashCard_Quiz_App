package com.yeminnaing.flashcardapp.data.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FlashCardEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class CardDataBase:RoomDatabase() {
  abstract fun flashCardDao():Dao
}