package com.salugan.gloapp.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.salugan.gloapp.data.local.AuthPreference
import com.salugan.gloapp.data.local.SessionModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val authPreference: AuthPreference
) : ViewModel() {

    fun getSession(): LiveData<SessionModel> {
        return authPreference.getSession().asLiveData()
    }
}