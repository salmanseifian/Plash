package com.salmanseifian.plash.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class DbUser(
    val email: String,
    val password: String,
    @ColumnInfo(name = "created_at") val createdAt: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}