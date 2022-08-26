package com.example.personalnotebook.screen.addnote

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentAddNoteBinding
import com.example.personalnotebook.model.Note
import com.example.personalnotebook.repository.NoteRepositoryImpl
import com.example.personalnotebook.screen.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {

    @Inject lateinit var noteRepository: NoteRepositoryImpl
    private val viewModel: AddNoteViewModel by viewModels()
    private val args: AddNoteFragmentArgs by navArgs()
    //use for add note or update note
    private var isUpdate = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutSignIn.tvUser.text = mAuth.currentUser?.email
        checkArgumentNote(args.note)

        binding.layoutSignIn.imgAvatar.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(R.id.action_global_loginFragment)
        }

        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnApply.setOnClickListener {
                val title = edTitle.text.toString()
                val description = edDescription.text.toString()
                val sdf = SimpleDateFormat("dd MMMM")
                val currentDate = sdf.format(Date())
                println(currentDate)

                val note = Note(id = args.note?.id, title = title, description = description, date = currentDate.toString())

                if(isUpdate)
                    viewModel.updateNote(note)
                else
                    viewModel.addNote(note)

                findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
            }
        }
    }

    private fun checkArgumentNote(note: Note?) {
        if(note != null) {
            binding.edTitle.setText(note.title)
            binding.edDescription.setText(note.description)
            isUpdate = true
        }
    }
}