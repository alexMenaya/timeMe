package com.alexmenaya.timeme.singletons

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

object DataController {

    private lateinit var appInstance : AppCompatActivity
    lateinit var appDatabase : RoomSingleton

    init {

        Log.i("DataController", "Created")

    }

    fun bind(appInstanceIn : AppCompatActivity) {

        Log.i("DataController", "Binding Application instance")
        this.appInstance = appInstanceIn
        this.appDatabase = RoomSingleton.getInstance(appInstanceIn)

    }

    @Database(entities = [

    ], version = 1, exportSchema = false)
    abstract class RoomSingleton : RoomDatabase() {

        companion object {

            private var INSTANCE : RoomSingleton? = null
            fun getInstance(context : Context) : RoomSingleton {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        RoomSingleton::class.java,
                        "roomDataBase"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                }

                return INSTANCE as RoomSingleton

            }

        }

    }

}