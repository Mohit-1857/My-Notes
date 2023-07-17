package com.example.mynotes.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mynotes.R
import com.example.mynotes.viewModel.NotesViewModel
import java.util.*

class AddTask : AppCompatActivity() {

    lateinit var saveBtn : Button
    lateinit var cancelBtn : Button
    lateinit var titleEdit : EditText
    lateinit var desEdit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.hide()

        titleEdit = findViewById(R.id.editTitle)
        desEdit = findViewById(R.id.editTxtDes)

        saveBtn = findViewById(R.id.btnSave)
        saveBtn.setOnClickListener{
            saveNote()
        }

        cancelBtn = findViewById(R.id.btnCancel)
        cancelBtn.setOnClickListener{
           finish()
        }
    }

    fun saveNote(){
        val title = titleEdit.text.toString()
        val des = desEdit.text.toString()

        val calendar : Calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.getDisplayName(Calendar.MONTH , Calendar.LONG , Locale.US)

        if(des.isEmpty() && title.isEmpty()){
            Toast.makeText(this,"Empty Note ",Toast.LENGTH_LONG).show()
        }
        else{
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("nTitle",title)
            intent.putExtra("nDes",des)
            intent.putExtra("nDate" , "$date $month")
            setResult(RESULT_OK,intent)
            finish()
        }
    }
}