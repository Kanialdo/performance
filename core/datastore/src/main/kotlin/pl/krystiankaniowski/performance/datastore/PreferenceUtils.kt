package pl.krystiankaniowski.performance.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Add string data to data Store */
internal suspend fun DataStore<Preferences>.writeString(key: String, value: String) {
    edit { pref -> pref[stringPreferencesKey(key)] = value }
}

/** Read string from the data store preferences */
internal fun DataStore<Preferences>.readString(key: String): Flow<String> {
    return data.map { pref ->
        pref[stringPreferencesKey(key)] ?: ""
    }
}

/** Add Integer to the data store */
internal suspend fun DataStore<Preferences>.writeInt(key: String, value: Int) {
    edit { pref -> pref[intPreferencesKey(key)] = value }
}

/** Reading the Int value from the data store */
internal fun DataStore<Preferences>.readInt(key: String): Flow<Int> {
    return data.map { pref ->
        pref[intPreferencesKey(key)] ?: 0
    }
}

/** Adding Double to the data store */
internal suspend fun DataStore<Preferences>.writeDouble(key: String, value: Double) {
    edit { pref -> pref[doublePreferencesKey(key)] = value }
}

/** Reading the double value from the data store */
internal fun DataStore<Preferences>.readDouble(key: String): Flow<Double> {
    return data.map { pref ->
        pref[doublePreferencesKey(key)] ?: 0.0
    }
}

/** Add Long to the data store */
internal suspend fun DataStore<Preferences>.writeLong(key: String, value: Long) {
    edit { pref -> pref[longPreferencesKey(key)] = value }
}

/** Reading the long from the data store */
internal fun DataStore<Preferences>.readLong(key: String): Flow<Long> {
    return data.map { pref ->
        pref[longPreferencesKey(key)] ?: 0L
    }
}


/** Add Boolean to the data store */
internal suspend fun DataStore<Preferences>.writeBool(key: String, value: Boolean) {
    edit { pref -> pref[booleanPreferencesKey(key)] = value }
}

/** Reading the Boolean from the data store */
internal fun DataStore<Preferences>.readBool(key: String): Flow<Boolean> {
    return data.map { pref ->
        pref[booleanPreferencesKey(key)] ?: false
    }
}