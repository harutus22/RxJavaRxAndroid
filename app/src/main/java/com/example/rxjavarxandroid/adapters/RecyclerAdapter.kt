package com.example.rxjavarxandroid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavarxandroid.R
import com.example.rxjavarxandroid.models.Post

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private val TAG = "RecyclerAdapter"
    private var posts = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_post_list_item, null, false))
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun setPosts(posts: ArrayList<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }

    fun updatePost(post: Post){
        posts[posts.indexOf(post)] = post
        notifyItemChanged(posts.indexOf(post))
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val numComments = itemView.findViewById<TextView>(R.id.number)
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)

        fun bind(post: Post){
            title.text = post.title
            if (post.comments == null){
                showProgressBar(true)
                numComments.text = ""
            } else{
                showProgressBar(false)
                numComments.text = post.comments?.size.toString()
            }
        }

        private fun showProgressBar(showProgressBar: Boolean){
            if (showProgressBar) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }
    }


}