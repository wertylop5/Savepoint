package io.github.wertylop5.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EntryViewModel(application: Application): AndroidViewModel(application) {
    private val repository: EntryRepository

    val noteEntries: LiveData<List<NoteEntry>>

    init {
        val noteEntryDao = AppDatabase.getInstance(application, viewModelScope).noteEntryDao()
        repository = EntryRepository(noteEntryDao)
        noteEntries = repository.noteEntries
    }

    fun insert(note: NoteEntry) = viewModelScope.launch {
        repository.insert(note)
    }
}