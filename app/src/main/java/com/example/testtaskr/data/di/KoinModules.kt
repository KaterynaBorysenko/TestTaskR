package com.example.testtaskr.data.di
import com.example.testtaskr.data.remote.RedditRemoteDataSource
import com.example.testtaskr.data.remote.RedditService
import com.example.testtaskr.data.repository.PostsRepository
import com.example.testtaskr.image_worker.ImageWriter
import com.example.testtaskr.ui.MainViewModel
import org.koin.android.ext.koin.androidContext

import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val mainModule = module {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.reddit.com/top.")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    fun provideRedditService(retrofit: Retrofit): RedditService {
        return retrofit.create(RedditService::class.java)
    }
    single { provideRetrofit() }
    single { provideRedditService(get()) }
    single { RedditRemoteDataSource(provideRedditService(get())) }
    single { PostsRepository(get()) }
    single { ImageWriter(androidContext()) }
    viewModel { MainViewModel(get()) }
}
