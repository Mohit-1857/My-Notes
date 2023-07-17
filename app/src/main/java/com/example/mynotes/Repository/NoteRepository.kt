package com.example.mynotes.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.mynotes.Model.Note
import com.example.mynotes.Room.NotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NoteRepository(private val noteDao : NotesDao) {

    val myAllNotes : Flow<List<Note>> = noteDao.getAllNotes()



    @WorkerThread
    suspend fun insert(note : Note){
        noteDao.insert(note)
    }

    @WorkerThread
    suspend fun update(note : Note){
        noteDao.update(note)
    }


    @WorkerThread
    suspend fun delete(note : Note){
        noteDao.delete(note)
    }


    @WorkerThread
    suspend fun deleteAll(){
        noteDao.deleteAll()
    }

    @WorkerThread
     fun getNotes(note : String) : Flow<List<Note>> {
         return noteDao.getNotes(note)
    }






}