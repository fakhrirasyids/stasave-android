package com.fakhrirasyids.stasave.core.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

internal class StasavePreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getWhatsappUri() = dataStore.data.map { it[WHATSAPP_URI_PREFERENCES] ?: "" }

    suspend fun saveWhatsappUri(
        whatsappUri: String
    ) {
        dataStore.edit { prefs ->
            prefs[WHATSAPP_URI_PREFERENCES] = whatsappUri
        }
    }

    suspend fun clearPreferences() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    companion object {
        fun get(dataStore: DataStore<Preferences>) = StasavePreferences(dataStore)

        private val WHATSAPP_URI_PREFERENCES = stringPreferencesKey("whatsapp_uri_preferences")
    }
}