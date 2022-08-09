package com.example.personalnotebook.repository

import com.example.personalnotebook.model.Note
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : NoteRepository {

    override suspend fun getNotes(userName: String): Result<List<Note>> {
        val myFef = database.getReference(userName)

        return runCatching { emptyList() }
    }

    override suspend fun insertNote(note: Note, userName: String) {
        val myRef = database.getReference(userName)

        myRef.setValue(note)
    }
}