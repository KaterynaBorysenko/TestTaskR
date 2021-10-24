package com.example.testtaskr.data.remote
import com.example.testtaskr.data.entities.RedditPostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("r/popular.json")
    suspend fun getTopPostsResponse(
        @Query("after") after: String,
        @Query("limit") limit: String): Response<RedditPostsResponse>

}