package com.example.testtaskr.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.FragmentManager
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testtaskr.data.entities.RedditChildrenResponse
import com.example.testtaskr.databinding.FragmentMainBinding
import com.example.testtaskr.databinding.RecycleViewItemPostBinding
import com.example.testtaskr.image_worker.ImageDialog
import com.example.testtaskr.utils.formatDate

class PagingTopPostsAdapter(
    val mainBinding: FragmentMainBinding,
    val layoutInflater: LayoutInflater,
    val fragmentManager: FragmentManager) :
    PagingDataAdapter<RedditChildrenResponse, PagingTopPostsAdapter.TopPostsViewHolder>(
        PostsComparator
    ) {
    private val context = mainBinding.root.context
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    inner class TopPostsViewHolder(private val binding: RecycleViewItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val postItem = getItem(position)?.data
            binding.itemPostTitle.text = postItem?.title ?: "Title is missing"
            binding.itemPostCommentsNumber.text = postItem?.num_comments.toString()
            binding.itemPostAuthor.text =
                postItem?.author ?: "Redditor"

            if (postItem != null) {
                binding.itemPostDate.text = formatDate(postItem.created_utc)
            }
            if (postItem?.thumbnail?.startsWith("https://") == true) {
                binding.itemPostThumbnail.visibility = ImageView.VISIBLE
                Glide.with(context).load(postItem.thumbnail).into(binding.itemPostThumbnail)
                binding.itemPostThumbnail.setOnClickListener {

                    Log.i("ItemURL", postItem.url)
                    if (getItem(position)?.data?.url!!.endsWith(".jpg") ||
                        getItem(position)?.data?.url!!.endsWith(".png")
                    ) {
                        val dialog = ImageDialog.newInstance(postItem.url)
                        dialog.show(fragmentManager, "TAG")
                    }
                }

            } else
                binding.itemPostThumbnail.visibility = ImageView.GONE
            mainBinding.mainProgressBar.visibility = ProgressBar.GONE
        }
    }

    override fun onBindViewHolder(holder: TopPostsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPostsViewHolder {
        val bdn = RecycleViewItemPostBinding.inflate(inflater, parent, false)
        return TopPostsViewHolder(bdn)
    }

    object PostsComparator : DiffUtil.ItemCallback<RedditChildrenResponse>() {
        override fun areItemsTheSame(
            oldItem: RedditChildrenResponse,
            newItem: RedditChildrenResponse): Boolean {
            return oldItem.data.url == newItem.data.url
        }
        override fun areContentsTheSame(
            oldItem: RedditChildrenResponse,
            newItem: RedditChildrenResponse
        ): Boolean {
            return oldItem.data == newItem.data
        }
    } }