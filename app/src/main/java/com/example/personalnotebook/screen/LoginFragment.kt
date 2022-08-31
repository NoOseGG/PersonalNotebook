package com.example.personalnotebook.screen

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentLoginBinding
import com.example.personalnotebook.extensions.changeIconColorForVisibleText
import com.example.personalnotebook.extensions.changeVisibleText
import com.example.personalnotebook.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentUser = mAuth.currentUser
        if (currentUser != null && currentUser.isEmailVerified) updateUI()

        with(binding) {

            btnLogin.setOnClickListener {
                val email = edLogin.text.toString()
                val password = edPassword.text.toString()

                if(email.isNotEmpty() && password.isNotEmpty()) {
                    signIn(email, password)
                    currentUser = mAuth.currentUser
                }

                if(currentUser?.isEmailVerified == false) {
                    showVerificationDialog()
                    return@setOnClickListener
                }

                updateUI()
            }

            btnRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }

            imgVisiblePassword.setOnClickListener {
                val result = edPassword.changeVisibleText()

                imgVisiblePassword.changeIconColorForVisibleText(
                    context = requireContext(),
                    result = result
                )
            }

        }
    }

    private fun updateUI() {
        findNavController().navigate(R.id.action_loginFragment_to_notesFragment)
    }

    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    println("SUCCESS")
                } else {
                    println("FAILURE")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showVerificationDialog() {
        val dialog = Dialog(requireContext(), R.style.AlertDialogTheme)
        dialog.setContentView(R.layout.layout_delete_verification)
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        dialog.findViewById<Button>(R.id.btn_no).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_yes).setOnClickListener {
            mAuth.currentUser?.sendEmailVerification()
            dialog.dismiss()
        }

        dialog.show()
    }
}