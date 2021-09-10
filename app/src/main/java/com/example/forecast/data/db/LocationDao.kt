package com.example.forecast.data.db

import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    fun getAll(): Single<List<Location>>

    @Query("SELECT * FROM locations WHERE id = (:locationId)")
    fun getById(locationId: Long): Single<Location>

    @Insert
    fun insert(location: Location): Single<Long>

    @Query("DELETE FROM locations WHERE id = :locationId")
    fun delete(locationId: Long): Single<Int>

    @Update
    fun update(location: Location): Single<Int>
}