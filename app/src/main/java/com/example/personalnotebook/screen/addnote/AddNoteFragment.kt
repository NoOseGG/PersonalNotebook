package com.example.personalnotebook.screen.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentAddNoteBinding
import com.example.personalnotebook.model.Note
import com.example.personalnotebook.repository.NoteRepositoryImpl
import com.example.personalnotebook.screen.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {

    @Inject lateinit var noteRepository: NoteRepositoryImpl
    private val viewModel: AddNoteViewModel by viewModels()
    private val args: AddNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUser.text = mAuth.currentUser?.email
        checkArgumentNote(args.note)

        binding.imgAvatar.setOnClickListener {
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
                val sdf = SimpleDateFormat("MMMM dd")
                val currentDate = sdf.format(Date())
                println(currentDate)

                val note = Note(title = title, description = description, date = currentDate.toString())

                viewModel.sendAddNote(note)
                findNavController().navigate(R.id.action_addNoteFragment_to_notesFragment)
            }
        }
    }

    private fun checkArgumentNote(note: Note?) {
        if(note != null) {
            binding.edTitle.setText(note.title)
            binding.edDescription.setText(note.description)
        }
    }
}