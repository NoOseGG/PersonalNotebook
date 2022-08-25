package com.example.personalnotebook.screen.notes

import androidx.lifecycle.ViewModel
import com.example.personalnotebook.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val database: FirebaseDatabase,
    private val mAuth: FirebaseAuth
) : ViewModel() {

    private val myRef = database.getReference(mAuth.currentUser?.email.toString().substringBefore("@"))
    private val list = mutableListOf<Note?>()

    val listFlow = MutableSharedFlow<List<Note?>>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1,
        replay = 1
    )

    init {
        loadData()
    }

    private fun loadData() {
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(list.size > 0) list.clear()
                for(ss in snapshot.children) {
                    list.add(0, ss.getValue(Note::class.java))
                }

                listFlow.tryEmit(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun deleteNote(note: Note) {
        list.remove(note)
        val path = mAuth.currentUser?.email?.substringBefore("@").toString()
        database.getReference(path).child(note.id!!)
            .removeValue()
        listFlow.tryEmit(list)
    }
}