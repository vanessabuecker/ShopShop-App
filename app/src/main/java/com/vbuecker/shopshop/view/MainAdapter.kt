package com.vbuecker.shopshop.view

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vbuecker.shopshop.R
import com.vbuecker.shopshop.model.ItemResponse
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter(val list: MutableList<ItemResponse> = arrayListOf()) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemResponse: ItemResponse) {

            with(itemView) {
                item_text_title.text = itemResponse.title
                item_text_desc.text = itemResponse.desc
                item_text_date.text = itemResponse.date.toString()

                val layerDrawable: LayerDrawable =
                    ContextCompat.getDrawable(context, R.drawable.bottom_type) as LayerDrawable

                val gradientDrawable: GradientDrawable =
                    layerDrawable.findDrawableByLayerId(R.id.mainDrawable) as GradientDrawable

                gradientDrawable.setStroke(6, itemResponse.type)
                item_container.background = layerDrawable
            }
        }
    }

}