package com.example.testtaskr.data.repository
import androidx.lifecycle.LiveData
import com.example.testtaskr.data.entities.RedditPostsResponse
import com.example.testtaskr.data.remote.RedditRemoteDataSource
import com.example.testtaskr.utils.Resource

class PostsRepository(private val remoteDataSource: RedditRemoteDataSource) {
    suspend fun getTopPostsResponseWithStatus(
        after: String,
        limit: String): Resource<RedditPostsResponse> =
        performGetOperation(networkCall = { remoteDataSource.getTopPostsResource(after, limit) })

    fun getTopPostsLiveData(after: String, limit: String): LiveData<Resource<RedditPostsResponse>> =
        performGetLiveDataOperation(networkCall = {
            remoteDataSource.getTopPostsResource(
                after,
                limit
            )
        })
}