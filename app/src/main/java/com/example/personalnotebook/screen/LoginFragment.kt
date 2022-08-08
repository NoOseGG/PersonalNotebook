package com.example.personalnotebook.screen

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = mAuth.currentUser
        if (currentUser != null) updateUI(currentUser)

        with(binding) {

            btnLogin.setOnClickListener {
                val email = edLogin.text.toString()
                val password = edPassword.text.toString()

                if(email.isNotEmpty() && password.isNotEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                user.text = getString(R.string.fragment_login_sign_in, task.result.user?.email)
                                imgAvatar.visibility = View.VISIBLE
                                findNavController().navigate(R.id.action_loginFragment_to_notesFragment)
                                println("SUCCESS")
                            } else {
                                println("FAILURE")
                            }
                        }
                }
            }

            btnRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }

       /* binding.imgAvatar.setOnClickListener {
            println(mAuth.currentUser?.email)
            mAuth.signOut()
            binding.imgAvatar.visibility = View.INVISIBLE
            println(mAuth.currentUser?.email)
            binding.user.text = ""
        }*/
    }

    private fun updateUI(user: FirebaseUser) {
        binding.user.text = user.email
        binding.imgAvatar.visibility = View.VISIBLE
        findNavController().navigate(R.id.action_loginFragment_to_notesFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.img_avatar -> {
                val v =  binding.imgAvatar as View
                val pm = PopupMenu(requireContext(), v)

                pm.menuInflater.inflate(R.menu.user_menu, pm.menu)
                pm.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.info_user -> Toast.makeText(requireContext(), "Info User", Toast.LENGTH_SHORT).show()
                    }
                    true
                })
                pm.show()
            }
        }
        return false
    }
}