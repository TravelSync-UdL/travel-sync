package com.app.travelsync.data

import android.content.Context
import android.content.SharedPreferences
import com.app.travelsync.utils.LanguageChangeUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManager @Inject constructor(
    private val preferences: SharedPreferences,
    @ApplicationContext private val context: Context
) {
    val languageChangeUtil by lazy { LanguageChangeUtil() }

    var userLanguage: String?
        get() = preferences.getString("user_language", "en")
        set(value) {
            preferences.edit().putString("user_language", value).apply()
            languageChangeUtil.changeLanguage(context, value ?: "en")
        }

    var userName: String?
        get() = preferences.getString("user_name", "")
        set(value) {
            preferences.edit().putString("user_name", value).apply()
        }

    var userSurname: String?
        get() = preferences.getString("user_surname", "")
        set(value) {
            preferences.edit().putString("user_surname", value).apply()
        }

    var userUsername: String?
        get() = preferences.getString("user_username", "")
        set(value) {
            preferences.edit().putString("user_username", value).apply()
        }

    var userEmail: String?
        get() = preferences.getString("user_email", "")
        set(value) {
            preferences.edit().putString("user_email", value).apply()
        }

    var userPassword: String?
        get() = preferences.getString("user_password", "")
        set(value) {
            preferences.edit().putString("user_password", value).apply()
        }
}
