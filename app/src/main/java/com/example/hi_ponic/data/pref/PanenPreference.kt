package com.example.hi_ponic.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.panenDataStore: DataStore<Preferences> by preferencesDataStore(name = "panen_prefs")

class PanenPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun savePanenData(date: String, result: Int) {
        dataStore.edit { preferences ->
            preferences[DATE_KEY] = date
            preferences[RESULT_KEY] = result
        }
    }

    fun getPanenData(): Flow<PanenModel> {
        return dataStore.data.map { preferences ->
            PanenModel(
                preferences[DATE_KEY] ?: "",
                preferences[RESULT_KEY] ?: -1
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PanenPreference? = null

        private val DATE_KEY = stringPreferencesKey("last_check_date")
        private val RESULT_KEY = intPreferencesKey("prediction_result")

        fun getInstance(dataStore: DataStore<Preferences>): PanenPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = PanenPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}

data class PanenModel(
    val lastCheckDate: String,
    val predictionResult: Int
)

data class cekkesehatan(
    val lastCheckDate: String,
)
