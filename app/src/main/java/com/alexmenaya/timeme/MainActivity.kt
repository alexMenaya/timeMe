package com.alexmenaya.timeme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alexmenaya.timeme.databinding.ActivityMainBinding
import com.alexmenaya.timeme.singletons.*
import com.alexmenaya.timeme.ui.main.MainFragment
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        AppController.bind(this)
        MessagingController.bind(this)
        DataController.bind(this)

        MessagingController.addObserver(NOTIFICATION_DEVICEINFO_UPDATED) { sender, dataobject ->
            Log.d("MainActivity", "Updated Device information from $sender!!")
            Log.d("MainActivity", "Received $dataobject!!")
        }

        MessagingController.addObserver(NOTIFICATION_RECEIVED) { sender, dataobject ->
            Log.d("MainActivity", "Received notification")
            Log.d("MainActivity", "Notification coming from $sender")
            Log.d("MainActivity", "Received $dataobject")
        }

        // Mechanism to post changes
        val data = JSONObject()
        MessagingController.postMessage("TESTING", this, data)

    }

}