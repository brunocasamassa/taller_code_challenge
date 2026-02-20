package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.apis.LoginApi
import com.example.myapplication.data.apis.LoginApiImpl
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.LoginDao
import com.example.myapplication.data.datasource.LoginDataSource
import com.example.myapplication.data.datasource.LoginDataSourceImpl
import com.example.myapplication.data.repositories.LoginRepository
import com.example.myapplication.data.repositories.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LoginModuleInjector {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "login_database"
        ).build()
    }

    @Provides
    fun provideLoginDao(database: AppDatabase): LoginDao {
        return database.loginDao()
    }

    @Provides
    @Singleton
    fun provideLoginApi(loginDao: LoginDao): LoginApi {
        return LoginApiImpl(loginDao)
    }

    @Provides
    @Singleton
    fun provideLoginDataSource(api: LoginApi): LoginDataSource {
        return LoginDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(dataSource: LoginDataSource): LoginRepository {
        return LoginRepositoryImpl(dataSource)
    }

}
