package com.salmanseifian.plash.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salmanseifian.plash.room.dao.NoteDao
import com.salmanseifian.plash.room.dao.NumberDao
import com.salmanseifian.plash.room.dao.UserDao
import com.salmanseifian.plash.room.models.DbDateFact
import com.salmanseifian.plash.room.models.Note
import com.salmanseifian.plash.room.models.DbUser


@Database(entities = [DbDateFact::class, DbUser::class, Note::class], version = 1)
abstract class MandalaDatabase : RoomDatabase() {
    abstract val numberDao: NumberDao
    abstract val userDao: UserDao
    abstract val noteDao: NoteDao
}


private lateinit var INSTANCE: MandalaDatabase

fun getDatabase(context: Context): MandalaDatabase {
    synchronized(MandalaDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE =
                Room.databaseBuilder(
                    context.applicationContext,
                    MandalaDatabase::class.java,
                    "numbers"
                )
                    .build()
        }

    }

    return INSTANCE
}