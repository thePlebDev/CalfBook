package com.elliottsoftware.calfbook.presentation.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calfbook.data.remote.Post
import com.elliottsoftware.calfbook.databinding.ItemTodoBinding

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostViewHolder>() {



    inner class PostViewHolder( val composeView:ComposeView):RecyclerView.ViewHolder(composeView){
        init {
            // This is from the previous guidance
            // NOTE: **Do not** do this with Compose 1.2.0-beta02+
            // and RecyclerView 1.3.0-alpha02+
            composeView.setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }
        fun bind(input:String){
            composeView.setContent {
                Text(input)
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)
    var posts:List<Post>
        get() = differ.currentList
        set(value){differ.submitList(value)}


    override fun getItemCount(): Int {
        return posts.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        return PostViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val current:Post = posts[position]

    }
    override fun onViewRecycled(holder: PostViewHolder) {
        // This is from the previous guidance
        // NOTE: You **do not** want to do this with Compose 1.2.0-beta02+
        // and RecyclerView 1.3.0-alpha02+
        holder.composeView.disposeComposition()
    }
}