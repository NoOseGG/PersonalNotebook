package com.example.personalnotebook.screen.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalnotebook.model.Note
import com.example.personalnotebook.repository.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepositoryImpl
) : ViewModel() {

    private val sendAddNoteFlow = MutableSharedFlow<Note>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    private val sendUpdateNoteFlow = MutableSharedFlow<Note>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    val addNoteFlow = sendAddNoteFlow.onEach {

    }.mapLatest { note ->
        noteRepository.insertNote(note)
        note
    }.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
        replay = 1
    )

    val updateNoteFlow = sendUpdateNoteFlow.onEach {

    }.mapLatest { note ->
        noteRepository.updateNote(note)
        note
    }.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
        replay = 1
    )

    fun addNote(note: Note) {
        sendAddNoteFlow.tryEmit(note)
    }

    fun updateNote(note: Note) {
        sendUpdateNoteFlow.tryEmit(note)
    }
}