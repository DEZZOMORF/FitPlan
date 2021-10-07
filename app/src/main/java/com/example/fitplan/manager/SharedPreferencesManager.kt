package com.example.fitplan.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager @Inject constructor(@ApplicationContext context: Context?) {

    private val SHARED_PREFERENCES_FILE_NAME = "fit_pref"
    private val ACCESS_TOKEN = "access_token"
    private val SHOWING_IMAGE_STATE = "showing image state"


    private val sharedPreferences: SharedPreferences? =
        context?.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    var userAccessToken: String?
        get() = sharedPreferences?.getString(ACCESS_TOKEN, null)
        set(token) {
            sharedPreferences?.edit {
                putString(ACCESS_TOKEN, token)
            }
        }

    var showingImageState: Boolean
        get() = sharedPreferences?.getBoolean(SHOWING_IMAGE_STATE, true) == true
        set(state) {
            sharedPreferences?.edit {
                putBoolean(SHOWING_IMAGE_STATE, state!!)
            }
        }
}