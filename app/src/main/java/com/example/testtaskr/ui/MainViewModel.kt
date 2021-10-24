package com.example.testtaskr.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testtaskr.data.entities.RedditChildrenResponse
import com.example.testtaskr.data.repository.PostsRepository
import com.example.testtaskr.data.repository.TopPostsPagingSource
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val postsRepository: PostsRepository) : ViewModel() {

    val allTopPostsPaging: Flow<PagingData<RedditChildrenResponse>> =
        Pager(
            PagingConfig(
                10, 10, false, 7
            )
        ) {
            TopPostsPagingSource(
                postsRepository
            )
        }.flow
            .cachedIn(viewModelScope)

    private var limit: String = "20"
    private var after: String = ""
    val allPostsStatus = postsRepository.getTopPostsLiveData(after, limit)
    fun setLimit(limit: String) {
        this.limit = limit
    }
    fun setAfter(after: String?) {
        this.after = after.toString()
    }
}
