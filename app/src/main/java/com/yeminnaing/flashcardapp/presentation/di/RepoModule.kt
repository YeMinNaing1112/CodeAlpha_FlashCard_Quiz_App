package com.yeminnaing.flashcardapp.presentation.di

import com.yeminnaing.flashcardapp.data.repositories.FlashCardRepoImpl
import com.yeminnaing.flashcardapp.domain.repositories.FlashCardRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindFlashCardRepoImpl(
        mFlashCardRepoImpl: FlashCardRepoImpl,
    ): FlashCardRepo
}