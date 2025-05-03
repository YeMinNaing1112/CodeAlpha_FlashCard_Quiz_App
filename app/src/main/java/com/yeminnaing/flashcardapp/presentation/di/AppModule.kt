package com.yeminnaing.flashcardapp.presentation.di

import android.content.Context
import androidx.room.Room
import com.yeminnaing.flashcardapp.data.roomdatabase.CardDataBase
import com.yeminnaing.flashcardapp.data.roomdatabase.daos.FlashCardDao
import com.yeminnaing.flashcardapp.data.roomdatabase.daos.MarksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):CardDataBase{
        return Room.databaseBuilder(
            context,
            CardDataBase::class.java,
            "flashcard_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFlashCardDao(db:CardDataBase): FlashCardDao {
     return  db.flashCardDao()
    }

    @Provides
    @Singleton
    fun provideMarksDao(db:CardDataBase):MarksDao{
        return db.marksDao()
    }
}