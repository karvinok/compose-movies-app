package com.karvinok.data.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class CacheManager(val context: Context, private val gson: Gson) {

    private val preferences = context.getSharedPreferences(
        PREF_ST_KEY, Context.MODE_PRIVATE
    )

    var apiKey: String
        get() = getFromCache(API_KEY, "")
        set(value) = saveToCache(API_KEY, value)

    private fun getFromCache(key: String, defaultValue: String): String {
        return preferences.getString(key, defaultValue)?: defaultValue
    }

    private fun getFromCache(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    private fun saveToCache(key: String, value: String?) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(key, value ?: "")
        editor.apply()
    }

    private fun saveToCache(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    companion object {
        private const val PREF_ST_KEY = "STORAGE_MAIN_KEY"
        private const val API_KEY = "api_key"
    }
}