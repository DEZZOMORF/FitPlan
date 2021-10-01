package com.example.fitplan.model

data class SettingsItem(
    val name: String,
    val type: SettingsType,
    val action: ActionType,
    var switched: Boolean? = null
)

enum class SettingsType {
    DEFAULT, SWITCH
}

enum class ActionType {
    IMAGE_SHOWING, LOGOUT
}