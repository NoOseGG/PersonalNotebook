package com.example.personalnotebook.screen.editnote

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentInfoNoteBinding
import com.example.personalnotebook.screen.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoNoteFragment : BaseFragment<FragmentInfoNoteBinding>(FragmentInfoNoteBinding::inflate) {

    private val args: InfoNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUser.text = mAuth.currentUser?.email

        binding.imgAvatar.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(R.id.action_global_loginFragment)
        }

        with(binding) {
            tvTextTitle.text = args.note?.title
            tvTextDescription.text = args.note?.description

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnEdit.setOnClickListener {
                val action = InfoNoteFragmentDirections.actionInfoNoteFragmentToAddNoteFragment(args.note)
                findNavController().navigate(action)
            }
        }
    }
}