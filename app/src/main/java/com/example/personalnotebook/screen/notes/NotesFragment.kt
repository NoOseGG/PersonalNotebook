package com.example.personalnotebook.screen.notes

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personalnotebook.R
import com.example.personalnotebook.adapter.NoteAdapter
import com.example.personalnotebook.adapter.NoteViewHolder
import com.example.personalnotebook.databinding.FragmentNotesBinding
import com.example.personalnotebook.databinding.LayoutDeleteDialogBinding
import com.example.personalnotebook.model.Note
import com.example.personalnotebook.screen.BaseFragment
import com.example.personalnotebook.screen.editnote.InfoNoteFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(FragmentNotesBinding::inflate) {

    private val list = mutableListOf<Note?>()
    private val adapter = NoteAdapter { note, button ->
        when(button) {
            NoteViewHolder.BUTTON_DELETE -> { showDeleteDialog(note) }
            NoteViewHolder.BUTTON_ROOT -> {
                val action = NotesFragmentDirections.actionNotesFragmentToInfoNoteFragment(
                    note = note
                )
                findNavController().navigate(action)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUser.text = mAuth.currentUser?.email

        loadData()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        binding.imgAvatar.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(R.id.action_global_loginFragment)
        }

        binding.btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
        }
    }

    private fun loadData() {
        val name = mAuth.currentUser?.email.toString().substringBefore("@")
        val myRef = database.getReference(name)

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(list.size > 0) list.clear()
                for(ss in snapshot.children) {
                    list.add(ss.getValue(Note::class.java))
                }

                adapter.submitList(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun deleteNote(note: Note) {
        list.remove(note)
        val path = mAuth.currentUser?.email?.substringBefore("@").toString()
        database.getReference(path).child(note.id!!)
            .removeValue()
        updateUi()
    }

    private fun updateUi() {
        adapter.submitList(list)
        binding.recyclerView.adapter = adapter
    }

    private fun showDeleteDialog(note: Note) {
        val dialog = Dialog(requireContext(), R.style.AlertDialogTheme)
        dialog.setContentView(R.layout.layout_delete_dialog)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        dialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_yes).setOnClickListener {
            deleteNote(note)
            dialog.dismiss()
        }

        dialog.show()
    }
}