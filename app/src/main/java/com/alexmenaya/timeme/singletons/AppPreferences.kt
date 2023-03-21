package com.alexmenaya.timeme.singletons

import android.annotation.SuppressLint
import android.content.Context
import kotlin.reflect.KProperty

class AppPreferences(context : Context) {

    private val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    var deviceId : String? by StringPref("device_id", null, blocking = true)
    var deviceInfo : String? by StringPref("device_info", null, blocking = true)

    class StringPref(
        private val key : String,
        private val default : String?,
        val blocking : Boolean = false) {

        operator fun getValue(
            preferencesService : AppPreferences,
            property : KProperty<*>
        ) : String? = preferencesService.sharedPreferences.getString(key, default) ?: default

        @SuppressLint("ApplySharedPref")
        operator fun setValue(
            preferencesService : AppPreferences,
            property : KProperty<*>,
            s : String?
        ) {

            if (blocking) {

                preferencesService.sharedPreferences.edit().putString(key, s).commit()

            } else {

                preferencesService.sharedPreferences.edit().putString(key, s).apply()

            }

        }

    }


}