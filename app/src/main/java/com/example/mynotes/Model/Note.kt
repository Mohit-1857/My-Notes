package com.example.mynotes.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "db_Notes")
class Note(val title : String , val description : String, val date : String ){


    @PrimaryKey(autoGenerate = true)
    var id = 0
}


