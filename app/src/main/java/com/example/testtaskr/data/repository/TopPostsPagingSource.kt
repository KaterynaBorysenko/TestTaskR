package com.example.testtaskr.data.repository
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testtaskr.data.entities.RedditChildrenResponse
import java.lang.Exception

class TopPostsPagingSource(
    private val repository: PostsRepository) :
    PagingSource<String, RedditChildrenResponse>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditChildrenResponse> {
        try {
            val refreshKey = params.key.toString()
            val responseLimit = params.loadSize
            val allPostsResponse =
                repository.getTopPostsResponseWithStatus(refreshKey, responseLimit.toString())
            val allPostsData = allPostsResponse.data?.data?.children ?: emptyList()
            return LoadResult.Page(
                data = allPostsData,
                prevKey = null,   //allPostsResponse.data?.data?.before,  //reddit api always returns null
                nextKey = allPostsResponse.data?.data?.after
            )

        } catch (e: Exception) {
            Log.e("TopPostsPagingSource", e.toString())
            return LoadResult.Error(e)
        }

    }
    override fun getRefreshKey(state: PagingState<String, RedditChildrenResponse>): String? {
        return null
    }
}