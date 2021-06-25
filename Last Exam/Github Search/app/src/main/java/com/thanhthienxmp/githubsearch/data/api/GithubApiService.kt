package com.thanhthienxmp.githubsearch.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiService {
    private const val BASE_URL = "https://api.github.com"

    // Setup OkHttp to listen the data response
    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    private fun getRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).client(okHttpClient).build()

    // Connect with Github Api
    val getService: GithubApi = getRetrofit().create(GithubApi::class.java)
}