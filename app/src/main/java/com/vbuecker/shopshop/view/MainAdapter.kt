package com.vbuecker.shopshop.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vbuecker.shopshop.R

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}