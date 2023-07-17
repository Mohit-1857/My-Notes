package com.example.mynotes.viewModel


import androidx.lifecycle.*
import com.example.mynotes.Model.Note
import com.example.mynotes.Repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {
    val myAllNotes : LiveData<List<Note>> = repository.myAllNotes.asLiveData()

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }
    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
     fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
     fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }




    fun searchQuery(searchQ : String) : LiveData<List<Note>>{
        return repository.getNotes(searchQ).asLiveData()
    }


}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(repository) as T
        }
        else{
            throw IllegalArgumentException("Unknown View Model")
        }
        return super.create(modelClass)
    }
}


