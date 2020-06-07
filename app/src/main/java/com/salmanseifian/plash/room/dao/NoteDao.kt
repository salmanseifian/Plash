package com.salmanseifian.plash.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.salmanseifian.plash.room.models.Note
import timber.log.Timber

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()


    @Transaction
    suspend fun deleteAllAndInsert(note: Note) {
        Timber.i("DELETING AND INSERTING DATA")
        deleteAll()
        insert(note)
    }

    @Transaction
    @Query("SELECT * FROM notes")
    fun getAll(): LiveData<List<Note>>
}