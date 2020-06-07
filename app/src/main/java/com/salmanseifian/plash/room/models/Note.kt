package com.salmanseifian.plash.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.salmanseifian.plash.extensions.today

@Entity(tableName = "notes")
data class Note(
    val title: String,
    val description: String?,
    val author: String,
    val priority: Int = 0,
    @ColumnInfo(name = "created_at")
    val createdAt: String = today()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}