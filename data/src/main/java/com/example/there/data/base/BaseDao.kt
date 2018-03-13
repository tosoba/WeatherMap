package com.example.there.data.base

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

@Dao
interface BaseDao<in T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(vararg t: T): Array<Long>
}