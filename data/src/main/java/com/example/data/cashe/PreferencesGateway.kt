package com.patient.data.cashe

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val PREFERENCES_NAME = "PREFERENCES_NAME"

class PreferencesGateway @Inject constructor(@ApplicationContext val context: Context) {

    inline fun <reified T : Any> save(key: String, value: T) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .apply { putValue(key, value) }
            .apply()
    }

    inline fun <reified T : Any> load(key: String, defaultValue: T): T? {
        return context
            .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .run { getValue(key, defaultValue) }
    }

    fun isSaved(key: String): Boolean {
        return context
            .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .contains(key)
    }
    fun remove(key: String) {
        context
            .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(key)
            .apply()
    }

    fun clearAll() {
        context
            .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }

    inline fun <reified T : Any> SharedPreferences.Editor.putValue(
        key: String,
        value: T
    ) {
        when (T::class) {
            Boolean::class -> putBoolean(key, value as Boolean)
            Int::class -> putInt(key, value as Int)
            Long::class -> putLong(key, value as Long)
            Float::class -> putFloat(key, value as Float)
            String::class -> putString(key, value as String)
            else -> throw UnsupportedOperationException("not supported preferences type")
        }
    }

    inline fun <reified T : Any?> SharedPreferences.getValue(
        key: String,
        defaultValue: T?
    ): T? {
        return when (T::class) {
            Boolean::class -> getBoolean(key, defaultValue as Boolean) as T
            Int::class -> getInt(key, defaultValue as Int) as T
            Long::class -> getLong(key, defaultValue as Long) as T
            Float::class -> getFloat(key, defaultValue as Float) as T
            String::class -> getString(key, defaultValue as String) as T
            else -> throw UnsupportedOperationException("not supported preferences type")
        }
    }
    inline fun <reified T : Any> update(key: String, value: T) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .apply { putValue(key, value) }
            .apply()
    }

    val gson: Gson = Gson()
    inline fun <reified T : Any> saveUser(key: String, value: T) {
        val json = gson.toJson(value)
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .apply { putString(key, json) }
            .apply()
    }
    fun saveUser(userData: Any) {
        userData.let {
//            save("all-user-data", it)
//            it.id?.let { it1 -> saveInt(ID, it1) }
//            it.name?.let { it1 -> saveString(NAME, it1) }
//            it.uid?.let { it1 -> saveString(UID, it1) }
//            it.email?.let { it1 -> saveString(EMAIL, it1) }
//            it.dob?.let { it1 -> saveString(DOB, it1) }
//            saveString(WALLETDI_AMONDS_COUNT, it.wallet?.diamond.toString())
//            it.pointsCount?.let { it1 -> saveString(POINTS_COUNT, it1) }
//            it.currentLevel?.let { it1 -> saveString(CURRENT_LEVEL, it1) }
//            it.gender?.let { it1 -> saveString(GENDER, it1) }
//            it.country?.name?.let { it1 -> saveString(COUNTRY, it1) }
//            it.currentProfileImageUrl?.let { it1 -> saveString(PROFILE_URL, it1) }
//            it.wallet?.gold?.let { saveString(GOLDEN_COUNT, it.toString()) }
//            it.agencyMembership?.let { it1 -> saveString(AGENCYMEMBER, it1) }
//            it.level?.icon?.let { it1 -> saveString(LEVEL_IMAGE, it1) }
//            it.bio?.let { it1 -> saveString(ABOUT, it1) }
//            userData.group?.id?.let { it1 -> saveInt(GROUP, it1) }
        }
    }

}