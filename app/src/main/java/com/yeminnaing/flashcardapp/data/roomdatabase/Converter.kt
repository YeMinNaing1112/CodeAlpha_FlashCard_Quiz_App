package com.yeminnaing.flashcardapp.data.roomdatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yeminnaing.flashcardapp.domain.model.QuestionModel

class Converter {
    @TypeConverter
    fun fromQuestionsToJson(questions:List<QuestionModel>):String{
        return Gson().toJson(questions)
    }

    @TypeConverter
    fun fromJsonToQuestions(json:String):List<QuestionModel>{
        val type =object : TypeToken<List<QuestionModel>>() {}.type
        return Gson().fromJson(json,type)
    }
}