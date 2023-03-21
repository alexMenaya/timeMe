package com.alexmenaya.timeme.singletons

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.util.UUID

const val NOTIFICATION_DEVICEINFO_UPDATED = "NOTIFICATION_DEVICEINFO_UPDATED"
//const val NOTIFICATION_NOTIFY_RECEIVED = "NOTIFICATION_NOTIFY_RECEIVED"
//const val NOTIFICATION_DATA_UPDATED = "NOTIFICATION_DATA_UPDATED"
const val NOTIFICATION_RECEIVED = "NOTIFICATION_RECEIVED"

object AppController {

    private lateinit var appInstance: AppCompatActivity

    private var deviceInfo : JSONObject?
        set(value) {
            if (value == null) {
                AppPreferences(appInstance).deviceInfo = null
            } else {
                AppPreferences(appInstance).deviceInfo = value.toString()
            }

            MessagingController.postMessage(NOTIFICATION_DEVICEINFO_UPDATED, this, value)
        }
        get() {
            val deviceRaw = AppPreferences(appInstance).deviceInfo
            if (deviceRaw != null) {
                return JSONObject(deviceRaw)
            }
            return null
        }

    private var deviceId : String?
        set(value) {
            val deviceInfo : JSONObject? = this.deviceInfo
            if (deviceInfo == null) {
                val valueOut : String? = value?.replace("-", "")
                AppPreferences(appInstance).deviceId = valueOut!!
                Log.i("AppController", "New device id assigned $value")
            } else {
                Log.i("AppController", "App reinstall")
            }
        }
        get() {
            val deviceInfo : JSONObject? = this.deviceInfo
            var currentValue : String?

            if (deviceInfo == null) {
                currentValue = AppPreferences(appInstance).deviceId
                if (currentValue.isNullOrEmpty()) {
                    val newId : String = UUID.randomUUID().toString()
                    currentValue = newId.replace("-", "")
                    this.deviceId = currentValue
                }
            } else {
                currentValue = deviceInfo.getString("uid")
            }

            return currentValue
        }

    init {

        Log.i("AppController", "Created")

    }

    fun bind(appInstanceIn : AppCompatActivity) {

        Log.i("AppController", "Binding Application instance")
        this.appInstance = appInstanceIn

    }

}