package io.github.wertylop5

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteEntryViewModel(application: Application): AndroidViewModel(application) {
    private val repository: NoteEntryRepository

    val noteEntries: LiveData<List<NoteEntry>>

    init {
        val noteEntryDao = AppDatabase.getInstance(application, viewModelScope).noteEntryDao()
        repository = NoteEntryRepository(noteEntryDao)
        noteEntries = repository.noteEntries
    }

    fun insert(note: NoteEntry) = viewModelScope.launch {
        repository.insert(note)
    }
}