package com.alexmenaya.timeme.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProjectDao {

    @Query("SELECT * FROM project WHERE is_deleted = 0")
    fun getAll(): List<Project>

    @Query("SELECT * FROM project WHERE uid in (:uid)")
    fun withUid(uid: String): Project?

    @Update
    fun update(vararg  poll: Project)

    @Insert
    fun insertAll(vararg poll: Project)

    @Delete
    fun delete(poll: Project)
}