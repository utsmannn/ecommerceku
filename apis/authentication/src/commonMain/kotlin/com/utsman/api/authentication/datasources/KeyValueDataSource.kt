package com.utsman.api.authentication.datasources

import com.russhwolf.settings.Settings


class KeyValueDataSource {

    private val settings: Settings = Settings()

    fun setString(key: String, value: String) {
        settings.putString(key, value)
    }

    fun getString(key: String): String {
        return settings.getString(key, "")
    }

    companion object {
        const val KEY_TOKEN = "key_token"
    }
}