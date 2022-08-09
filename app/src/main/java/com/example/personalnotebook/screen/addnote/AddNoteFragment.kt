package com.example.personalnotebook.screen.addnote

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentAddNoteBinding
import com.example.personalnotebook.model.Note
import com.example.personalnotebook.repository.NoteRepositoryImpl
import com.example.personalnotebook.screen.BaseFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {

    @Inject lateinit var noteRepository: NoteRepositoryImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUser.text = mAuth.currentUser?.email

        with(binding) {

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnApply.setOnClickListener {
                val title = edTitle.text.toString()
                val note = Note(database.reference.key, title)
                val name: String = mAuth.currentUser?.email?.substringBefore("@").toString()

                val ref = database.getReference(name)
                ref.push().setValue(note)
                findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
            }

        }

    }
}