package com.example.myapplication.domain

import com.example.myapplication.data.entities.ResponseStatus

data class LoginDTO(
    val userName: String,
    val email: String,
    val status: ResponseStatus?
)
