package com.alexmenaya.timeme.singletons

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

object MessagingController {

    private lateinit var appInstance : AppCompatActivity
    // TODO: refine this Any
    private var listeners = mutableMapOf<String, MutableList<Any>>()

    init {

        Log.i("MessagingController", "Created")

    }

    fun bind(appInstanceIn : AppCompatActivity) {

        Log.i("MessagingController", "Binding Application instance")
        this.appInstance = appInstanceIn

    }

    fun addObserver(
        message : String,
        unit : (sender : Any, dataobject : Any?) -> Unit
    ) : Boolean {

        val messageListeners : MutableList<Any>? =
            if(this.listeners.keys.contains(message)) {
                listeners[message]
            } else {
                mutableListOf()
            }

        messageListeners!!.add(unit)
        listeners[message] = messageListeners
        return true

    }

    // Todo: see if I can refine this Any
    fun postMessage(
        message : String,
        sender : Any,
        dataobject : Any?
    ) {

        var messageListeners : MutableList<Any>? = null

        if (this.listeners.keys.contains(message)) {

            messageListeners = listeners[message]

        }

        if (messageListeners != null) {

            for (unit : Any in messageListeners) {

                val callback = unit as (sender : Any, dataobject : Any?) -> Unit

                callback(sender, dataobject)

            }

        }

    }

}