package com.alexmenaya.timeme.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE is_deleted = 0")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task WHERE uid in (:uid)")
    fun withUid(uid: String): Task?

    @Query("SELECT * FROM task WHERE time_ended = 0 AND is_deleted = 0")
    fun selectOpenTask(): Task?

    @Update
    fun update(vararg  poll: Task)

    @Insert
    fun insertAll(vararg poll: Task)

    @Delete
    fun delete(poll: Task)
}