package com.example.recipeapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.recipeapp.auth.dao.UserDao
import com.example.recipeapp.auth.local.UserLocalSource
import com.example.recipeapp.auth.local.UserLocalSourceImpl
import com.example.recipeapp.auth.repo.UserRepository
import com.example.recipeapp.auth.repo.UserRepositoryImpl
import com.example.recipeapp.db.RecipeDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.reflect.KParameter

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {
    @Singleton
    @Provides
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RecipeDatabase::class.java,
            "recipe_db"
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideUserDao(db: RecipeDatabase): UserDao = db.userDao()
    @Provides
    fun providesUserLocalSource(@ApplicationContext context: Context): UserLocalSource{
        return UserLocalSourceImpl(context)
    }
    @Provides
    fun providesUserRepository(localSource: UserLocalSource): UserRepository{
        return UserRepositoryImpl(localSource)
    }
}