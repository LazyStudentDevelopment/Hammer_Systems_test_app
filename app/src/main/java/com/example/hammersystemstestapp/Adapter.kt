package com.example.hammersystemstestapp
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hammersystemstestapp.beer.Beer
import com.example.hammersystemstestapp.databinding.RecyclerviewItemBinding
import com.squareup.picasso.Picasso
import retrofit2.Response.error


class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Beer>() {
        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var beers: List<Beer>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = beers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val beer = beers[position]
            name.text = beer.hops.name
            Picasso.get()
                .load("${beer.image_url}")
                .into(imageUrl)
        }
    }
}