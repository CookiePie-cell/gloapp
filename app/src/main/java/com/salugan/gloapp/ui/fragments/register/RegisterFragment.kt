package com.salugan.gloapp.ui.fragments.register

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
import com.salugan.gloapp.databinding.FragmentRegisterBinding
import com.salugan.gloapp.ui.fragments.login.LoginFragment
import com.salugan.gloapp.data.Result

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel by viewModels<RegisterViewModel> {
        RegisterViewModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mFragmentManager = parentFragmentManager
        val mLoginFragment = LoginFragment()

        binding.btnLogin.setOnClickListener {
            mFragmentManager.commitNow(allowStateLoss = true) {
                replace(R.id.auth_fragment, mLoginFragment, LoginFragment::class.java.simpleName)
            }
        }

        binding.register.setOnClickListener {
            val name = binding.editTextNameReg.text.toString()
            val email = binding.editTextEmailReg.text.toString()
            val phone = binding.editTextPhoneReg.text.toString()
            val password = binding.editTextPasswordReg.text.toString()

            if (name.isEmpty()) {
                binding.editTextNameReg.error = getString(R.string.error_empty_field)
            }
            if (email.isEmpty()){
                binding.editTextEmailReg.error = getString(R.string.error_empty_field)
            }
            if (phone.isEmpty()) {
                binding.editTextPhoneReg.error = getString(R.string.error_empty_field)
            }
            if (password.isEmpty()) {
                binding.editTextPasswordReg.error =getString(R.string.error_empty_field)
            } else {
                if (password.length >= 6) {
                    registerViewModel.register(name, email, phone, password).observe(requireActivity()) { result ->
                        when(result) {
                            is Result.Loading -> showLoading(true)
                            is Result.Success -> {
                                showLoading(false)
                                Toast.makeText(requireActivity(), getString(R.string.success_reg), Toast.LENGTH_SHORT).show()
                                mFragmentManager.commitNow(allowStateLoss = true) {
                                    replace(R.id.auth_fragment, mLoginFragment, LoginFragment::class.java.simpleName)
                                }
                            }
                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(requireActivity(), getString(R.string.failed_regis), Toast.LENGTH_SHORT).show()
                                Log.d("wkwk", result.error)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
            binding.register.isEnabled = false
            binding.btnLogin.isEnabled = false
        } else {
            binding.progressBar2.visibility = View.GONE
            binding.register.isEnabled = true
            binding.btnLogin.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}