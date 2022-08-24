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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(FragmentNotesBinding::inflate) {

    private val viewModel: NotesViewModel by viewModels()
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

        with(binding) {
            tvUser.text = mAuth.currentUser?.email
            recyclerView.smoothScrollToPosition(0)
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.setHasFixedSize(false)
            recyclerView.adapter = adapter

            imgAvatar.setOnClickListener {
                mAuth.signOut()
                findNavController().navigate(R.id.action_global_loginFragment)
            }

            btnAddNote.setOnClickListener {
                findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
            }
        }

        viewModel.listFlow.onEach { list ->
            updateUi(list)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun updateUi(list: List<Note?>) {
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
            viewModel.deleteNote(note)
            dialog.dismiss()
        }

        dialog.show()
    }
}