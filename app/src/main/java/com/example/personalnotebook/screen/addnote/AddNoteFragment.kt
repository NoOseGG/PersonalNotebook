package com.example.personalnotebook.screen.addnote

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentAddNoteBinding
import com.example.personalnotebook.model.Note
import com.example.personalnotebook.repository.NoteRepositoryImpl
import com.example.personalnotebook.screen.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {

    @Inject lateinit var noteRepository: NoteRepositoryImpl
    private val viewModel: AddNoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUser.text = mAuth.currentUser?.email

        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnApply.setOnClickListener {
                val title = edTitle.text.toString()
                val description = edDescription.text.toString()
                val note = Note(title = title, description = description)

                viewModel.sendAddNote(note)
                findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
            }
        }

    }
}