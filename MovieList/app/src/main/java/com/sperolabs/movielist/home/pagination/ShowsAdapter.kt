package com.sperolabs.movielist.home.pagination

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.sperolabs.movielist.R
import com.sperolabs.movielist.model.TvShow
import kotlinx.android.synthetic.main.show_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ShowsAdapter(val onClick : (Int) -> Unit) : PagingDataAdapter<TvShow, ShowViewHolder>(DiffUtilCallback) {
    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder =
        ShowViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.show_item, parent, false), onClick)

}

class ShowViewHolder(val item : View, val onClick : (Int) -> Unit) : RecyclerView.ViewHolder(item){

    fun bind(show : TvShow){
        item.title.text = show.name
        item.network_name.text = show.network
        item.country_name.text = show.country

        item.show_status.text = show.status

        Glide.with(item.context)
            .load(show.thumbnail)
            .into(item.image)

        item.card_view.setOnClickListener { onClick(show.id) }
    }

}

object DiffUtilCallback : DiffUtil.ItemCallback<TvShow>(){
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
        oldItem == newItem

}