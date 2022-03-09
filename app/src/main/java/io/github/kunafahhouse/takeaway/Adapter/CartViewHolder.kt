package io.github.kunafahhouse.takeaway.Adapter


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.kunafahhouse.takeaway.Interface.ItemClickListener

class CartViewHolder : RecyclerView.ViewHolder, View.OnClickListener {

    var cartProductName: TextView
    var cartProductPrice: TextView
    var cartBtnCount: ImageView

    lateinit var itemClickListener: ItemClickListener

     constructor(itemView: View,
                cartProductName: TextView,
                cartProductPrice: TextView,
                cartBtnCount: ImageView) : super(itemView) {

        this.cartProductName = cartProductName
        this.cartProductPrice = cartProductPrice
        this.cartBtnCount = cartBtnCount

        itemView.setOnClickListener(this)

    }

    fun setitemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    override fun onClick(v: View?) {
        if(adapterPosition != RecyclerView.NO_POSITION) {
            itemClickListener.onClick(v!!, adapterPosition, false)
        }
    }


}