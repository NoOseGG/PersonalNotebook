package com.example.personalnotebook.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val mAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLoginBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }



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
                                println("SUCCESS")
                            } else {
                                println("FAILURE")
                            }
                        }
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            println(mAuth.currentUser?.email)
            mAuth.signOut()
            println(mAuth.currentUser?.email)
            binding.user.text = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(user: FirebaseUser) {
        binding.user.text = user.email
    }
}