package com.example.personalnotebook.screen.notes

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentNotesBinding
import com.example.personalnotebook.screen.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(FragmentNotesBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUser.text = mAuth.currentUser?.email

        binding.imgAvatar.setOnClickListener {
            mAuth.signOut()
            findNavController().navigate(R.id.action_global_loginFragment)
        }
    }

}