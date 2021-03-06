package io.github.kunafahhouse.takeaway.Adapter


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.kunafahhouse.takeaway.Interface.ItemClickListener

class MenuViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

    var categoryImg: ImageView
    var categoryName: TextView
    lateinit var itemClickListener: ItemClickListener

    constructor(itemView: View, categoryImg: ImageView, categoryName: TextView) : super(itemView) {
        this.categoryImg = categoryImg
        this.categoryName = categoryName
        itemView.setOnClickListener(this)
    }

    fun setitemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition, false)
    }


}