package com.salugan.gloapp.data.local

data class SessionModel(
    val username: String,
    val token: String,
    val isLogin: Boolean
)