package com.alexmenaya.timeme.singletons

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexmenaya.timeme.MainActivity
import com.alexmenaya.timeme.data.Project
import com.alexmenaya.timeme.data.ProjectDao
import com.alexmenaya.timeme.data.Task
import com.alexmenaya.timeme.data.TaskDao

object DataController {

    private lateinit var appInstance: MainActivity
    lateinit var appDatabase: RoomSingleton

    fun bind(
        appInstanceIn: MainActivity
    ) {
        Log.i(this.javaClass.toString(), "Binding Application Instance")
        appInstance = appInstanceIn
        appDatabase = RoomSingleton.getInstance(appInstanceIn)
    }
}

@Database(
    entities = [
        Task::class,
        Project::class
    ],
    version = 2,
    exportSchema = false
)
abstract class RoomSingleton: RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun projectDao(): ProjectDao

    companion object {

        private var INSTANCE: RoomSingleton? = null

        fun getInstance(
            context: Context
        ): RoomSingleton {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context = context,
                    klass = RoomSingleton::class.java,
                    "roomdb"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as RoomSingleton
        }
    }
}