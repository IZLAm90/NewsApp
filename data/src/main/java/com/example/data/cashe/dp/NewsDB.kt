package com.example.data.cashe.dp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model.NewData

@Database(entities = [NewData::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class NewsDB : RoomDatabase(){
    abstract fun articleDao(): ArticleDao
}