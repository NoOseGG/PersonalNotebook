package com.example.personalnotebook.repository

import com.example.personalnotebook.mapper.toEntity
import com.example.personalnotebook.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val mAuth: FirebaseAuth
) : NoteRepository {

    override suspend fun getNotes(userName: String): Result<List<Note>> {
        val myFef = database.getReference(userName)

        return runCatching { emptyList() }
    }

    override suspend fun updateNote(note: Note) {
        println("NOTE:      $note")
        val userName = mAuth.currentUser?.email.toString().substringBefore("@")
        val myRef = database.getReference(userName)
        println(note.id)
        myRef.child(note.id.toString()).child("title").setValue(note.title)
        myRef.child(note.id.toString()).child("description").setValue(note.description)

    }

    override suspend fun insertNote(note: Note) {
        println(note)
        val name: String = mAuth.currentUser?.email?.substringBefore("@").toString()
        val ref = database.getReference(name)
        val id = ref.push().key
        note.id = id
        println(note)
        ref.child(id!!).setValue(note)
    }
}