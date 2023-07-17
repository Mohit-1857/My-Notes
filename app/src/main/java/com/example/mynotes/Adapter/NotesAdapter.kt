package com.example.mynotes.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.Model.Note
import com.example.mynotes.R
import com.example.mynotes.View.MainActivity
import com.example.mynotes.View.Update

class NotesAdapter(private val activity: MainActivity) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(),
    Adapter {

    var notes : List<Note> = ArrayList()

    class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val textTitle : TextView = itemView.findViewById(R.id.txtTitle)
        val textDes : TextView = itemView.findViewById(R.id.txtVDes)
        val textDate : TextView = itemView.findViewById(R.id.txtDate)
        val cardView : CardView = itemView.findViewById(R.id.cardV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val view : View = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.notes_list,parent,false)

        return NoteViewHolder(view)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentNote : Note = notes[position]

        holder.textTitle.text = currentNote.title
        holder.textDes.text = currentNote.description
        holder.textDate.text = currentNote.date


        holder.cardView.setOnClickListener{
            val intent = Intent(activity,Update::class.java)
            intent.putExtra("currentTitle" , currentNote.title)
            intent.putExtra("currDes",currentNote.description)
            intent.putExtra("currId" , currentNote.id)
            activity.updateActivityResultLauncher.launch(intent)
        }
    }



    override fun getItemCount(): Int {
        return  notes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNote(myNotes : List<Note> ){
        this.notes = myNotes
        notifyDataSetChanged()
    }

    fun getNote(position: Int) : Note{
        return notes[position]
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }


}

