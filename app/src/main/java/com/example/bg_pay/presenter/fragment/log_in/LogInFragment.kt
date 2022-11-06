package com.example.bg_pay.presenter.fragment.log_in

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bg_pay.R
import com.example.bg_pay.common.BaseFragment
import com.example.bg_pay.databinding.FragmentLogInBinding
import com.example.bg_pay.utility.snack
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()

    override fun listeners() {
        binding.btnLogin.setOnClickListener {

            when {
                isEmptyField() -> it.snack(getString(R.string.empty_fields_error))
                !isValidEmail() -> it.snack(getString(R.string.invalid_email_error))
                else -> {
                    login()
                    loginObserver()
                }
            }
        }
    }

    override fun init() {}

    override fun observers() {}

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.loginResponse(email = email, password = password)
    }

    private fun loginObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginStatus.collectLatest {
                    if (it.message!!.isNotEmpty()) {
                        Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG)
                            .show()
                        if (it.message.toString() == getString(R.string.login_success)) {
                            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
                        }
                    }
                }
            }
        }
    }

    private fun isValidEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()

    private fun isEmptyField(): Boolean = with(binding) {
        return@with binding.etEmail.text.toString().isEmpty() ||
                binding.etPassword.text.toString().isEmpty()
    }

}