package io.github.kunafahhouse.takeaway.Adapter

import android.content.Context
import android.graphics.Color

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import io.github.kunafahhouse.takeaway.Model.Order
import io.github.kunafahhouse.takeaway.R
import java.text.NumberFormat
import java.util.*


class CartAdapter(private val context: Context, private val orders: List<Order>) : RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.cart_item, parent, false)
        val name = view.findViewById<TextView>(R.id.txtNameProductCart)
        val price = view.findViewById<TextView>(R.id.txtPriceProductCart)
        val btn_img = view.findViewById<ImageView>(R.id.btn_cart_count)
        return CartViewHolder(view, name, price, btn_img)
    }

    override fun getItemCount(): Int {
        return orders.count()
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val textDrawable = TextDrawable.builder()
                .buildRound("" + orders[position].quantity, Color.RED)
        holder.cartBtnCount.setImageDrawable(textDrawable)

        val price = (orders[position].price.toDouble()) *
                (orders[position].quantity.toDouble())

        val locale = Locale("en", "CY")
        val nf = NumberFormat.getCurrencyInstance(locale)

        holder.itemView.setOnClickListener {


        }
        holder.cartProductPrice.text = nf.format(price)
        holder.cartProductName.text = orders[position].productName

    }



}