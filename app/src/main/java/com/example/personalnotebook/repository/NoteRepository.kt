package com.example.personalnotebook.repository

import com.example.personalnotebook.model.Note

interface NoteRepository {

    suspend fun getNotes(UserID: String): Result<List<Note>>

    suspend fun insertNote(note: Note)
}