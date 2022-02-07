package com.moya.core.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val PREFERENCES_NAME = "SAMPLE_APP_PREFERENCES"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    private fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(preferences.edit()) {
            block()
            commit()
        }
    }

    fun <T> put(key: String, value: T) {
        when (value) {
            is String -> edit { putString(key, value) }
            is Int -> edit { putInt(key, value) }
            is Long -> edit { putLong(key, value) }
            is Float -> edit { putFloat(key, value) }
            is Boolean -> edit { putBoolean(key, value) }
            else -> edit { putString(key, value.toString()) }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, defaultValue: T): T {
        return preferences.run {
            when (defaultValue) {
                is String -> getString(key, defaultValue) as T
                is Int -> getInt(key, defaultValue) as T
                is Long -> getLong(key, defaultValue) as T
                is Float -> getFloat(key, defaultValue) as T
                is Boolean -> getBoolean(key, defaultValue) as T
                else -> getString(key, defaultValue.toString()) as T
            }
        }
    }

    fun putToken(token: String) {
        put(PreferencesConstants.KEY_TOKEN, token)
    }

    fun getToken(): String {
        return get(PreferencesConstants.KEY_TOKEN, "")
    }

}