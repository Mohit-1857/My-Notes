package com.example.mynotes.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mynotes.R
import java.util.*

class Update : AppCompatActivity() {

    lateinit var updateBtn : Button
    lateinit var cancelBtn : Button
    lateinit var titleUEdit : EditText
    lateinit var desUEdit : EditText

     var currentId = -1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        //supportActionBar?.title = "Update Note"
        supportActionBar?.hide()

        titleUEdit = findViewById(R.id.editUTitle)
        desUEdit = findViewById(R.id.editUTxtDes)
        updateBtn = findViewById(R.id.btnUpdate)
        cancelBtn = findViewById(R.id.btnUCancel)

        getAndSetData()


        updateBtn.setOnClickListener{
            updateNote()
        }


        cancelBtn.setOnClickListener{
            finish()
        }
    }

     fun updateNote(){
        val updatedTitle : String  = titleUEdit.text.toString()
        val updatedDes : String = desUEdit.text.toString()

         val calendar : Calendar = Calendar.getInstance()
         val udate = calendar.get(Calendar.DAY_OF_MONTH)
         val umonth = calendar.getDisplayName(Calendar.MONTH , Calendar.LONG , Locale.US)

         val intent = Intent()
         intent.putExtra("updatedTitleN" , updatedTitle)
         intent.putExtra("updatedDesN",updatedDes)
         intent.putExtra("updatedDate", "$udate $umonth")

        if(updatedTitle.isEmpty() || updatedDes.isEmpty()){
            Toast.makeText(this,"Empty Note ", Toast.LENGTH_LONG).show()
        }
         else{

            if(currentId != -1){
                intent.putExtra("noteId" , currentId)
                setResult(RESULT_OK,intent)
                finish()
            }
        }
    }

    fun getAndSetData(){
        val currTitle = intent.getStringExtra("currentTitle")
        val currDesc = intent.getStringExtra( "currDes")
        currentId = intent.getIntExtra("currId" , -1)
        titleUEdit.setText(currTitle)
        desUEdit.setText(currDesc)

    }
}