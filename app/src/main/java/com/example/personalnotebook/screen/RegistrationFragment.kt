package com.example.personalnotebook.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.personalnotebook.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationFragment : Fragment() {

    private val mAuth by lazy { FirebaseAuth.getInstance() }

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRegistrationBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createNewUserInFirebase(login: String, password: String) {

        mAuth.createUserWithEmailAndPassword(login, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    showToast("Вы зарегистрировались")
                    findNavController().popBackStack()
                } else {
                    showToast("Вы не смогли зарегистрироваться")

                }
            }
    }

    private fun checkEqualsPassword(firstPassword: String, secondPassword: String): Boolean {
        if(firstPassword.equals(secondPassword)) {
            return true
        }
        showToast("Пароли не совпадают")
        return false
    }

    private fun checkCorrectPassword(password: String): Boolean {
        if(password.length < 6) {
            showToast("Пароль слишком короткий")
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}