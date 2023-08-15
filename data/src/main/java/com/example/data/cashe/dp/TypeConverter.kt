package com.example.data.cashe.dp

import androidx.room.TypeConverter
import com.example.data.model.NewData
import com.example.data.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TypeConverter {
    val gson : Gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?) : NewData? {
        if(data == null)return null
        val listType: Type = object :TypeToken<NewData?>() {}.type
        return gson.fromJson<NewData?>(data, listType)
    }

    @TypeConverter
    fun jsonToSource(json: String): Source = gson.fromJson(json, Source::class.java)


    @TypeConverter
    fun someObjectListToString(someobject: NewData?): String?
    {
        return gson.toJson(someobject)
    }
}
