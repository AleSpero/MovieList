package com.sperolabs.movielist.home.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sperolabs.movielist.R
import com.sperolabs.movielist.model.TvShow
import kotlinx.android.synthetic.main.show_item.view.*

class ShowsAdapter : PagingDataAdapter<TvShow, ShowViewHolder>(DiffUtilCallback) {

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder =
        ShowViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.show_item, parent, false))

}

class ShowViewHolder(val item : View) : RecyclerView.ViewHolder(item){

    fun bind(show : TvShow){
        item.title.text = show.name
    }

}

object DiffUtilCallback : DiffUtil.ItemCallback<TvShow>(){
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
        oldItem == newItem

}