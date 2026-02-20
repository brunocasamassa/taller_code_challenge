package com.example.myapplication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginEntity(
    @PrimaryKey
    val id: Int,
    val userName:String,
    val email:String,
    val status: ResponseStatus

)


enum class RESPONSE_STATUS {
    STATUS_OK,
    STATUS_ERROR
}

/*using typealias as mentioned at the interview, gracias */
typealias ResponseStatus = RESPONSE_STATUS
