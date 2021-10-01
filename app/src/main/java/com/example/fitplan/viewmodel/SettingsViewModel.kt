package com.example.fitplan.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fitplan.model.SettingsItem
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : ViewModel() {

    var settings: HashSet<SettingsItem> = linkedSetOf()
    init {
        settings.add(SettingsItem("Enable showing image", true))
        settings.add(SettingsItem("Logout", false))
    }

}