package com.example.myapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.entities.LoginEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogin(login: LoginEntity)

    @Query("SELECT * FROM LoginEntity LIMIT 1")
    fun getLoggedUser(): Flow<LoginEntity?>

    @Query("DELETE FROM LoginEntity")
    suspend fun deleteLogin()
}
