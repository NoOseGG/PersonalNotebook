package com.example.personalnotebook.manager

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManager @Inject constructor(
    @ApplicationContext context: Context
) {

    val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var nightMode: NightMode by enumPrefs(KEY_NIGHT_MODE, NightMode.SYSTEM)

    private inline fun <reified E : Enum<E>> enumPrefs(key: String, defaultValue: E) =
        PrefsDelegate(
            sharedPreferences,
            getValue = { getString(key, null)?.let(::enumValueOf) ?: defaultValue },
            setValue = { putString(key, it.name) }
        )

    companion object {
        private const val PREF_NAME = "prefs"
        private const val KEY_NIGHT_MODE = "night_mode"
    }
}