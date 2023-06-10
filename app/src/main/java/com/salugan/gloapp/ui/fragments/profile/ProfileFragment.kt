package com.salugan.gloapp.ui.fragments.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.salugan.gloapp.databinding.FragmentProfileBinding
import com.salugan.gloapp.data.Result

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel by viewModels<ProfileViewModel> {
        ProfileViewModelFactory.getInstance(requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireActivity()
        profileViewModel.getSession().observe(context) { sessionModel ->
            val username = sessionModel.username
            profileViewModel.getUser(username).observe(context) { result ->
                when(result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        binding.apply {
                            usernameEditText.setText(result.data.username)
                            emailEditText.setText(result.data.email)
                            phoneEditText.setText(result.data.mobile.toString())
                        }
                    }
                    is Result.Error -> {}
                }
            }
        }


        binding.btnLogout.setOnClickListener {
            profileViewModel.clearSession()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}