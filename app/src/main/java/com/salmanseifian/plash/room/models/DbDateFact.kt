package com.salmanseifian.plash.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datefacts")
data class DbDateFact constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val year: Int,
    val number: Int
)
