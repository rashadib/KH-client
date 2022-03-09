package io.github.kunafahhouse.takeaway.Controller

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import io.github.kunafahhouse.takeaway.DB.Database
import io.github.kunafahhouse.takeaway.Model.Order
import io.github.kunafahhouse.takeaway.Model.Product
import io.github.kunafahhouse.takeaway.R
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    lateinit var productId: String
    lateinit var database: FirebaseDatabase
    lateinit var productRef: DatabaseReference
    lateinit var product: Product


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // some changes

       product = (intent.getSerializableExtra("product") as? Product)!!
       val productTotalPrice = intent.getDoubleExtra("productTotalPrice",0.0)


        product.price = productTotalPrice.toString()
        productId = product.name.toString()
        loadProductDetails(product,productTotalPrice)

       saveToCart()
    }

    private fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }


    private fun loadProductDetails(product: Product?, productTotalPrice: Double) {
        collapsing.title = product?.name
        product_name.text = product?.name
        product_price.text = productTotalPrice.toString()
        product_description.text = product?.description
    }

    private fun loadDetailProduct(productId: String) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                product = dataSnapshot.getValue(Product::class.java)!!

                Picasso.get()
                        .load(product.image)
                        .placeholder(R.mipmap.kh_bg)
                        .error(R.mipmap.kh_bg)
                        .into(product_img)

                collapsing.title = product.name

                product_name.text = product.name
                product_price.text = product.price
                product_description.text = product.description

            }

        }
        productRef.child(productId).addValueEventListener(valueEventListener)
    }

    private fun saveToCart() {
        btn_add_cart.setOnClickListener {
            Database(this)
                    .addToCart(Order(
                        extra=product.extra!!,
                        productName= product.name!!,
                        quantity=  btn_number.number,
                        price=  product.price!!,
                        size =  product.size!!
                    ))

            "${product.name} added to cart".toast(this)
            startActivity(Intent(this@ProductDetailActivity,CartActivity::class.java))
            finish()
        }
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

}
