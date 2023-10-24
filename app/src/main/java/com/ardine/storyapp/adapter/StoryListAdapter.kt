package com.ardine.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.core.util.Pair
import com.ardine.storyapp.R
import com.ardine.storyapp.data.response.ListStoryItem
import com.ardine.storyapp.databinding.ItemStoryBinding
import com.ardine.storyapp.view.detail.DetailActivity
import com.bumptech.glide.Glide


class StoryListAdapter(private val listStory: List<ListStoryItem>, private val token :String) : RecyclerView.Adapter<StoryListAdapter.ViewHolder>(){
    class ViewHolder(var binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = listStory[position]
        val token = token

        holder.apply {
            binding.apply {
                tvTitle.text = story.name
                tvDesc.text = story.description

                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .error(R.drawable.image_dicoding)
                    .into(ivStory)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TOKEN, token)
                    intent.putExtra(DetailActivity.EXTRA_ID, story.id)
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(ivStory, "photoStory"),
                        Pair(tvTitle, "title"),
                        Pair(tvDesc, "description")
                    )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun getItemCount(): Int = listStory.size

}