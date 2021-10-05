package com.example.fitplan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitplan.manager.SharedPreferencesManager
import com.example.fitplan.model.ActionType
import com.example.fitplan.model.SettingsItem
import com.example.fitplan.model.SettingsType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _settings = MutableLiveData(generateSettingsList())
    val settings: LiveData<MutableList<SettingsItem>> get() = _settings
    lateinit var logout: () -> Unit

    private fun generateSettingsList(): MutableList<SettingsItem> {
        return mutableListOf(
            SettingsItem(
                "Enable showing image",
                SettingsType.SWITCH,
                ActionType.IMAGE_SHOWING,
                sharedPreferencesManager.showingImageState
            ),
            SettingsItem(
                "Logout",
                SettingsType.DEFAULT,
                ActionType.LOGOUT
            )
        )
    }

    fun onSettingClicked(item: SettingsItem, index: Int) {
        when (item.action) {
            ActionType.IMAGE_SHOWING -> {
                val imageSwitched = !sharedPreferencesManager.showingImageState
                sharedPreferencesManager.showingImageState = imageSwitched
                _settings.value = settings.value?.apply {
                    item.switched = imageSwitched
                    set(index, item)
                }
            }
            ActionType.LOGOUT -> {
                sharedPreferencesManager.userAccessToken = ""
                logout()
            }
        }
    }
}