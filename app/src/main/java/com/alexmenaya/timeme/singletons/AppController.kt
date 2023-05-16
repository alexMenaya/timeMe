package com.alexmenaya.timeme.singletons

import android.util.Log
import com.alexmenaya.timeme.MainActivity
import org.json.JSONObject
import java.math.BigInteger
import java.security.MessageDigest
import java.util.UUID

object AppController {

    private lateinit var appInstance: MainActivity

    var deviceId: String?
        set(value) {
            val deviceInfo: JSONObject? = deviceInfo
            if (deviceInfo == null) {
                val valueOut: String? = value?.replace("-", "")
                MyPreferences(appInstance).deviceId = valueOut!!
                Log.i("AppController", "Assigned new device id $value")
            } else {
                Log.i("AppController", "Discard attempt to change deviceId - reinstall app")
            }
        }
        get() {
            val deviceInfo: JSONObject? = deviceInfo
            var currentValue: String?

            if (deviceInfo == null) {
                currentValue = MyPreferences(appInstance).deviceId
                if (currentValue.isNullOrEmpty()) {
                    val newId: String = UUID.randomUUID().toString()
                    currentValue = newId.replace("-", "")
                    deviceId = currentValue
                }
            } else {
                currentValue = deviceInfo.getString("uid")
            }
            return currentValue
        }

    var deviceInfo: JSONObject?
        set(value) {
            if (value != null) {
                MyPreferences(appInstance).deviceInfo = value.toString()
            } else {
                MyPreferences(appInstance).deviceInfo = null
            }
        }
        get() {
            val deviceRaw = MyPreferences(appInstance).deviceInfo
            if (deviceRaw != null) {
                return JSONObject(deviceRaw)
            }
            return null
        }

    val userId: String?
        get() {
            val deviceInfo: JSONObject? = deviceInfo
            var currentValue: String? = null

            if (deviceInfo != null) {
                currentValue = deviceInfo.getString("id_person")
            }
            return currentValue
        }

    val shortCode: String
        get() {
            return shortCodeForUid(deviceId)
        }

    private fun shortCodeForUid(
        uid: String?
    ): String {
        val codeOut = uid?.substring(20)
        var shortCodeOut = ""

        if (codeOut != null) {
            var counter = 0
            while (counter < codeOut.length) {
                if (shortCodeOut.isNotEmpty()) shortCodeOut += "."
                var end: Int = counter + 4
                if (end > codeOut.length) end = codeOut.length -1
                shortCodeOut += codeOut.substring(counter, end)
                counter += 4
            }
        }

        return shortCodeOut.uppercase()
    }

    fun bind(
        appInstanceIn: MainActivity
    ) {
        Log.i("AppController", "Binding Application Instance")
        appInstance = appInstanceIn
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16)
            .padStart(32, '0')
    }
}