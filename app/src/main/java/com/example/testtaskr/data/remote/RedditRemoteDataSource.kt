package com.example.testtaskr.data.remote

class RedditRemoteDataSource(private val redditService: RedditService) : BaseDataSource() {
    suspend fun getTopPostsResource(after: String, limit: String) =
        getResults { redditService.getTopPostsResponse(after, limit) }
}