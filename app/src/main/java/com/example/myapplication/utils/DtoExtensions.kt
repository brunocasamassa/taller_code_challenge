package com.example.myapplication.utils

import com.example.myapplication.data.entities.LoginEntity
import com.example.myapplication.domain.LoginDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun LoginEntity.toDTO(): LoginDTO {
    return LoginDTO(
            userName = this.userName,
            email = this.email,
            status = this.status
        )

}
