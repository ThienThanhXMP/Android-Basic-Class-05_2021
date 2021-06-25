package com.thanhthienxmp.githubsearch.data.api

import com.thanhthienxmp.githubsearch.data.model.GithubAccount
import com.thanhthienxmp.githubsearch.data.model.GithubFollowAccount
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    // Get user from the username passed
    @GET("/users/{user_id}")
    suspend fun getUser(@Path("user_id") user_id: String): Response<GithubAccount>

    @GET("/users/{user_id}/followers")
    suspend fun getUserFollowers(
        @Path("user_id") user_id: String,
        @Query("per_page") per_page: Int
    ): Response<GithubFollowAccount>

    @GET("/users/{user_id}/following")
    suspend fun getUserFollowing(
        @Path("user_id") user_id: String,
        @Query("per_page") per_page: Int
    ): Response<GithubFollowAccount>
}