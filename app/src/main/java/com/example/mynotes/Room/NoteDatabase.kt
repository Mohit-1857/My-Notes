package com.example.mynotes.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mynotes.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Note::class] , version = 2)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNotesDao() : NotesDao

    // Singleton

    companion object{

        val migration_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE db_Notes ADD COLUMN date STRING NOT NULL DEFAULT(1)")
            }
        }

        @Volatile
        private var INSTANCE : NoteDatabase? = null

        fun getDatabase(context: Context, applicationScope: CoroutineScope) : NoteDatabase{
            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(context.applicationContext,
                NoteDatabase::class.java,"note_Database")
                    .addMigrations(migration_1_2)
                    .build()

                INSTANCE = instance
                instance
            }

        }

    }

    private class NoteDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                //database.getNotesDao().insert(Note("t"))
                scope.launch {
                    val noteDao = database.getNotesDao()
                    noteDao.insert(Note("Title 1 " , "Description 1") )
                }
            }
        }
    }

}