package com.example.personalnotebook.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.personalnotebook.R
import com.example.personalnotebook.databinding.FragmentRegistrationBinding
import com.example.personalnotebook.extensions.changeIconColorForVisibleText
import com.example.personalnotebook.extensions.changeVisibleText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnRegistration.setOnClickListener {
                val login = edLogin.text.toString()
                val firstPassword = edFirstPassword.text.toString()
                val secondPassword = edSecondPassword.text.toString()

                if(checkCorrectPassword(firstPassword) &&
                    checkEqualsPassword(firstPassword, secondPassword)) {

                    createNewUserInFirebase(login, firstPassword)
                }
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            imgVisiblePasswordFirst.setOnClickListener {
                changeIconColorForVisibleText(imgVisiblePasswordFirst, edFirstPassword)
            }
            imgVisiblePasswordSecond.setOnClickListener {
                changeIconColorForVisibleText(imgVisiblePasswordSecond, edSecondPassword)
            }
        }
    }

    private fun createNewUserInFirebase(login: String, password: String) {
        mAuth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {

                    showToast(getString(R.string.registred))
                    findNavController().popBackStack()
                } else {
                    showToast(getString(R.string.not_registred))
                }
            }
    }

    private fun checkEqualsPassword(firstPassword: String, secondPassword: String): Boolean {
        if(firstPassword.equals(secondPassword)) {
            return true
        }
        showToast(getString(R.string.password_dont_equals))
        return false
    }

    private fun checkCorrectPassword(password: String): Boolean {
        if(password.length < 6) {
            showToast(getString(R.string.password_so_short))
            return false
        }
        return true
    }

    private fun changeIconColorForVisibleText(image: ImageView, editText: EditText) {
        val result = editText.changeVisibleText()

        image.changeIconColorForVisibleText(
            context = requireContext(),
            result = result
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}