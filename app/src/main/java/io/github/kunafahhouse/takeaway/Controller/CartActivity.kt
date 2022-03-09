package io.github.kunafahhouse.takeaway.Controller

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.github.kunafahhouse.takeaway.Adapter.CartAdapter
import io.github.kunafahhouse.takeaway.DB.Database
import io.github.kunafahhouse.takeaway.Interface.ItemClickListener
import io.github.kunafahhouse.takeaway.Model.Order
import io.github.kunafahhouse.takeaway.Model.Request
import io.github.kunafahhouse.takeaway.R
import io.github.kunafahhouse.takeaway.Utils.Common
import kotlinx.android.synthetic.main.activity_cart.*
import java.text.NumberFormat
import java.util.*


class CartActivity : AppCompatActivity(){

    lateinit var database: FirebaseDatabase
    lateinit var requestRef: DatabaseReference
    lateinit var adapter: CartAdapter
    lateinit var carts: List<Order>
    var total = 0.0
    val locale = Locale("en", "CY")
    val nf = NumberFormat.getCurrencyInstance(locale)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        //firebase init
        database = FirebaseDatabase.getInstance()
        requestRef = database.getReference("Request")

        loadListProduct()
        fabClearCart.setOnClickListener {
            Database(this).cleanCart()
            adapter.notifyDataSetChanged()
            "Cart is EMPTY".toast(this)
            finish()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun loadListProduct() {
        carts = Database(this).getCarts()
        adapter = CartAdapter(this, carts)
        val manager = LinearLayoutManager(this)
        recyclerview_cart.layoutManager = manager
        recyclerview_cart.setHasFixedSize(true)
        recyclerview_cart.adapter = adapter
        
        //total of price

        for (order in carts) {
            total += (order.price.toDouble()) * (order.quantity.toDouble())
        }

        total_cart_price.text = nf.format(total)

        btn_buy.setOnClickListener {
            if (carts.isEmpty())
                "Please, add product to your cart.".toast(this)
            else
                showDialog()

        }
        btn_addItem.setOnClickListener {
            startActivity(Intent(this,ProductListActivity::class.java))
        }
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.setTitle("Checkout")
        dialog.setContentView(R.layout.dialog_request)
        dialog.findViewById<TextView>(R.id.confirm_cart_price).text = nf.format(total)
        dialog.findViewById<TextView>(R.id.txt_confirm_order_name).text = Common.currentUser!!.name
        dialog.findViewById<TextView>(R.id.txt_confirm_order_phone).text = Common.currentUser!!.phone

        val comment = dialog.findViewById<EditText>(R.id.txt_confirm_order_comment)

        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.cancel()
        }
        dialog.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            val request = Request(
                    Common.currentUser!!.phone,
                    Common.currentUser!!.name,
                    comment.text.toString(),
                    total_cart_price.text.toString(),
                    carts

            )

            //submit to firebase

            val requestKey = System.currentTimeMillis()
            requestRef.child(requestKey.toString()).setValue(request)

            //delete cart
            Database(this).cleanCart()
            "Your confirmation is successful.".toast(this)
            startActivity(Intent(this@CartActivity,ProductListActivity::class.java))
            finish()
        }

        dialog.show()

    }


    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }


}
