package com.example.mynotes.Room

import androidx.room.*
import com.example.mynotes.Model.Note
import kotlinx.coroutines.flow.Flow



@Dao
interface NotesDao {

    @Insert
    suspend fun insert(note  : Note)

    @Update
    suspend fun update(note : Note)

    @Delete
    suspend fun delete(note : Note)

    @Query("DELETE FROM db_Notes")
    suspend fun deleteAll()

    @Query("SELECT * FROM db_notes ORDER BY id DESC")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * FROM db_notes WHERE title LIKE   :searchQuery")
    fun getNotes(searchQuery : String) : Flow<List<Note>>

}