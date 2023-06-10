package com.salugan.gloapp.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commitNow
import androidx.fragment.app.viewModels
import com.salugan.gloapp.R
import com.salugan.gloapp.databinding.FragmentLoginBinding
import com.salugan.gloapp.ui.fragments.register.RegisterFragment
import com.salugan.gloapp.data.Result
import com.salugan.gloapp.ui.activities.MainActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mFragmentManager = parentFragmentManager
        val mRegisterFragment = RegisterFragment()

        binding.btnRegister.setOnClickListener {
            mFragmentManager.commitNow(allowStateLoss = true) {
                replace(R.id.auth_fragment, mRegisterFragment, RegisterFragment::class.java.simpleName)
            }
        }

        binding.login.setOnClickListener {
            val username = binding.editTextName.text.toString().trim()
            val password = binding.editTextPassword.text.toString()

            Log.d("asdasd", username)
            if (username.isEmpty()){
                binding.editTextName.error = getString(R.string.error_empty_field)
            }
            if (password.isEmpty()) {
                binding.editTextPassword.error = getString(R.string.error_empty_field)
            }else {
                if (password.length >= 6) {
                    viewModel.login(username, password).observe(requireActivity()) { result ->
                        when(result) {
                            is Result.Loading -> showLoading(true)
                            is Result.Success -> {
                                showLoading(false)
                                Toast.makeText(requireActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show()

                                startActivity(Intent(requireActivity(), MainActivity::class.java))
                                requireActivity().finish()
                            }
                            is Result.Error -> {
                                showLoading(false)
                                Log.d("asdasd", result.error)
                                Toast.makeText(requireActivity(), getString(R.string.failed_login), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.login.isEnabled = false
            binding.btnRegister.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.login.isEnabled = true
            binding.btnRegister.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}