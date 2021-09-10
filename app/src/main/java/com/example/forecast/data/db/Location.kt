package com.example.forecast.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(@ColumnInfo(name = "name")val name: String,
                    @ColumnInfo(name = "latitude")val latitude: Double,
                    @ColumnInfo(name = "longitude")val longitude: Double) {
    @PrimaryKey(autoGenerate = true) var id: Long? = null
}