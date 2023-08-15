package com.example.data.cashe.dp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.NewData


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<NewData>)

    @Query("SELECT * FROM AllNews")
    suspend fun getAllArticle(): List<NewData>

    @Query("DELETE FROM AllNews")
    suspend fun clearTable()

}