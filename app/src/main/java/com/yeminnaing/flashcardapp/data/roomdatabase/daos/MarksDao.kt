package com.yeminnaing.flashcardapp.data.roomdatabase.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeminnaing.flashcardapp.data.roomdatabase.entities.MarksEntity
@Dao
interface MarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarks(marks: MarksEntity)

    @Query("SELECT * FROM Marks")
    suspend fun getAllMarks(): List<MarksEntity>

    @Query("DELETE  FROM Marks WHERE flashCardId= :id  ")
    suspend fun deleteById(id:Int)
}