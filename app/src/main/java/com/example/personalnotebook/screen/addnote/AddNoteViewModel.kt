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

    private val sendNoteFlow = MutableSharedFlow<Note>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    val resultFlow = sendNoteFlow.onEach {

    }.mapLatest {
        val result = noteRepository.insertNote(it)
        result
    }.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
        replay = 1
    )

    fun sendAddNote(note: Note) {
        sendNoteFlow.tryEmit(note)
    }
}