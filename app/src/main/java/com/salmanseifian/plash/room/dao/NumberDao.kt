package com.salmanseifian.plash.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.salmanseifian.plash.custom.aliases.ListOfDateFacts
import com.salmanseifian.plash.room.models.DbDateFact

@Dao
interface NumberDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(DbDateFact: DbDateFact)


    @Query("select * from datefacts ORDER BY year")
    fun getDateFacts(): LiveData<ListOfDateFacts>
}