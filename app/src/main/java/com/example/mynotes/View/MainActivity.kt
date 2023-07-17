package com.example.mynotes.View

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.Adapter.NotesAdapter
import com.example.mynotes.Model.Note
import com.example.mynotes.NotesApplication
import com.example.mynotes.R
import com.example.mynotes.viewModel.NoteViewModelFactory
import com.example.mynotes.viewModel.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener {

    private lateinit var notesViewModel: NotesViewModel
    lateinit var addbtn : FloatingActionButton
    private val noteAdapter : NotesAdapter by lazy { NotesAdapter(this) }
    lateinit var addActivityResultLauncher : ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher : ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addbtn = findViewById(R.id.btnAdd)
        addbtn.setOnClickListener{
            val intent = Intent(this,AddTask::class.java)
            addActivityResultLauncher.launch(intent)
        }



        val recyclerView : RecyclerView = findViewById(R.id.Notes_recylerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter





        registerActivityResultLauncher()



        val viewModelFactory = NoteViewModelFactory((application as NotesApplication).repository)

        notesViewModel = ViewModelProvider(this,viewModelFactory).get(NotesViewModel::class.java)

        notesViewModel.myAllNotes.observe(this) { note: List<Note> ->
            noteAdapter.setNote(note)
        }


        ItemTouchHelper(object  : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("Delete Note")
                dialog.setMessage("Are you sure you want to delete Note?")
                dialog.setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
                    notesViewModel.delete((noteAdapter).getNote(viewHolder.adapterPosition))
                })
                dialog.setNegativeButton("Cancel" , DialogInterface.OnClickListener { dialogInterface, i ->

                    val intent = Intent(this@MainActivity,MainActivity::class.java)
                    startActivity(intent)

                })

                window.setBackgroundDrawableResource(android.R.color.transparent)


                dialog.create().show()

            }

        }).attachToRecyclerView(recyclerView)


    }

     private fun registerActivityResultLauncher(){
        addActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                val resultCode = it.resultCode
                val data = it.data

                if(resultCode == RESULT_OK && data != null){
                    val title : String = data.getStringExtra("nTitle").toString()
                    val des : String = data.getStringExtra("nDes").toString()
                    val date : String = data.getStringExtra("nDate").toString()

                    val note = Note(title , des , date )
                    notesViewModel.insert(note)
                }

            })

         updateActivityResultLauncher = registerForActivityResult(
             ActivityResultContracts.StartActivityForResult(),
             ActivityResultCallback {
                 val resultCode = it.resultCode
                 val data = it.data

                 if(resultCode == RESULT_OK && data != null){
                        val updatedTitle : String = data.getStringExtra("updatedTitleN").toString()
                     val updatedDes : String = data.getStringExtra("updatedDesN").toString()
                     val updatedDate : String = data.getStringExtra("updatedDate").toString()
                     val noteId : Int = data.getIntExtra("noteId" , -1)


                     val newNote = Note(updatedTitle , updatedDes , updatedDate)

                     newNote.id = noteId

                     notesViewModel.update(newNote)
                 }

             })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_fragment, menu)
//        val search = menu?.findItem(R.id.search)
//        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.deleteAll ->{
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("Delete All Note")
                dialog.setMessage("Are you sure you want to delete all Note?")
                dialog.setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
                    notesViewModel.deleteAll()
                })
                dialog.setNegativeButton("Cancel" , DialogInterface.OnClickListener { dialogInterface, i ->
                    1
                })

                window.setBackgroundDrawableResource(android.R.color.transparent)


                dialog.create().show()


                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if(p0 != null){
            searchNote(p0)
        }
        return true
    }

    private fun searchNote(query : String){


        val sQuery = "%$query%"
        notesViewModel.searchQuery(sQuery).observe(this) { list ->
            list.let {
                noteAdapter.setNote(it)
            }
        }
   }



}