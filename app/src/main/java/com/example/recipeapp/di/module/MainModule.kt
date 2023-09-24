package com.example.recipeapp.di.module

import android.content.Context
import com.example.recipeapp.db.RecipeDatabase
import com.example.recipeapp.main.dao.FavoriteDao
import com.example.recipeapp.main.dao.MealDao
import com.example.recipeapp.main.local.LocalSource
import com.example.recipeapp.main.local.LocalSourceImpl
import com.example.recipeapp.main.network.MealRemoteDataSource
import com.example.recipeapp.main.network.MealRemoteDataSourceImpl
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.FavoriteRepositoryImpl
import com.example.recipeapp.main.repo.MealsRepository
import com.example.recipeapp.main.repo.MealsRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Provides
    fun providesFavoriteDao(db: RecipeDatabase): FavoriteDao = db.favoriteDao()
    @Provides
    fun providesMealDao(db: RecipeDatabase): MealDao = db.mealDao()
    @Provides
    fun providesLocalSource(@ApplicationContext context: Context): LocalSource{
        return LocalSourceImpl(context)
    }
    @Provides
    fun providesGson(): Gson = GsonBuilder().serializeNulls().create()
    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    @Singleton
    @Provides
    fun bindsMealRemoteDataSource(retrofit: Retrofit): MealRemoteDataSource{
        return MealRemoteDataSourceImpl(retrofit)
    }
    @Provides
    fun bindsFavoriteRepository(local: LocalSource): FavoriteRepository{
        return FavoriteRepositoryImpl(local)
    }
    @Provides
    fun providesMealRepository(local: LocalSource, remote: MealRemoteDataSource): MealsRepository{
        return MealsRepositoryImpl(remote, local)
    }
}