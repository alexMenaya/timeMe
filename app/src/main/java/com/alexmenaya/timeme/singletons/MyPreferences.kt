package com.alexmenaya.timeme.singletons

import android.content.Context
import kotlin.reflect.KProperty

class MyPreferences(
    context: Context
) {

    private val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    var deviceId: String? by StringPref("device_id", null, blocking = true)
    var deviceInfo: String? by StringPref("device_info", null, blocking = true)

    class StringPref(
        private val key: String,
        private val default: String?,
        val blocking: Boolean = false
    ) {

        operator fun getValue(
            preferencesService: MyPreferences,
            property: KProperty<*>
        ): String? = preferencesService.sharedPreferences.getString(key, default) ?: default

        operator fun setValue(
            preferencesService: MyPreferences,
            property: KProperty<*>,
            s: String?
        ) {
            if (blocking) {
                preferencesService.sharedPreferences.edit().putString(key, s).commit()
            } else {
                preferencesService.sharedPreferences.edit().putString(key, s).apply()
            }
        }
    }
}