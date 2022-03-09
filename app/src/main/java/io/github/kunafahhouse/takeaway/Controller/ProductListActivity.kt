package io.github.kunafahhouse.takeaway.Controller

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import io.github.kunafahhouse.takeaway.Interface.ItemClickListener
import io.github.kunafahhouse.takeaway.Model.Product
import io.github.kunafahhouse.takeaway.R

import kotlinx.android.synthetic.main.activity_food_list.*
import kotlinx.android.synthetic.main.app_bar_home.*
import java.lang.Exception

class ProductListActivity : AppCompatActivity() ,ItemClickListener{


    var chosenLastPrice = 0.00
    var recyclerView:RecyclerView? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var drawerView: DrawerLayout? = null
    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)



        drawerView =  findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this,drawerView, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerView?.addDrawerListener(toggle!!)
        toggle?.syncState()

        initMenu()
        fillMenu()

        recyclerView = findViewById(R.id.recyclerviewFoodList)
        recyclerView?.adapter = adapter

        adapter.setOnItemClickListener { item, view ->
            chosenLastPrice = 0.00
            var product = item as ProductItem
            showAlertDialog(product.product)


        }
        fabCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

    }



    private fun fillMenu() {
        adapter.add(ProductItem(Product("Classic Kunafah With Cheese","https://imageproxy.wolt.com/menu/menu-images/60acb64fe4f563caf02a9d78/e203e8fe-c769-11eb-9de3-2a36d515bd46_5b0f650a_c384_11eb_bf0c_c2839bd53c06_classic_knafeh_with_cheese.jpeg",
            "Our best seller!","4.00","0","0","medium","")))
        adapter.add(ProductItem(Product("Kunafah Nabulsi Fine With Cheese","https://imageproxy.wolt.com/menu/menu-images/60acb64fe4f563caf02a9d78/e203e8fe-c769-11eb-9de3-2a36d515bd46_5b0f650a_c384_11eb_bf0c_c2839bd53c06_classic_knafeh_with_cheese.jpeg",
            "Smooth fine Kunafah","4.00","0","1","medium","")))
        adapter.add(ProductItem(Product("Kunafah With Pistachio","https://imageproxy.wolt.com/menu/menu-images/60acb64fe4f563caf02a9d78/82864bbc-c384-11eb-b4e5-9a2d3766412d_knafeh_with_pistachio_2.jpeg",
            "Our best seller!","5.00","0","2","medium","")))
        adapter.add(ProductItem(Product("Kunafah With Cheese & Nutella","https://imageproxy.wolt.com/menu/menu-images/60acb64fe4f563caf02a9d78/79364aee-c384-11eb-b26f-82e6eddd413d_knafeh_with_cheese_and_nutella.jpeg",
            "Nutella is always a sweet choice, even better with some cheese :)","4.50","0","3","medium","")))
        adapter.add(ProductItem(Product("Kunafah With Nutella","https://imageproxy.wolt.com/menu/menu-images/60acb64fe4f563caf02a9d78/8a2d66ac-c384-11eb-929b-920e20a8af6e_knafeh_with_nutella.jpeg",
            "Kunafah With Nutella","4.00","0","4","medium","")))
        adapter.add(ProductItem(Product("Kunafah With Nuts & Cinnamon","https://imageproxy.wolt.com/menu/menu-images/60acb64fe4f563caf02a9d78/6fe1d8dc-c384-11eb-b382-76c95c3e9346_knafeh_with_nuts_and_cinamon.jpeg",
            "Kunafah With Nuts & Cinnamon","4.00","0","5","medium","")))
        adapter.add(ProductItem(Product("Kunafah With Lotus","https://imageproxy.wolt.com/menu/menu-images/60acb64fe4f563caf02a9d78/90db559a-c384-11eb-83e3-6acb1d788e4a_knafeh_with_lotus.jpeg",
            "Kunafah With Lotus","4.00","0","6","medium","")))
        adapter.add(ProductItem(Product("Kunafah With Lotus","https://imageproxy.wolt.com/menu/menu-images/60acb64fe4f563caf02a9d78/c058019c-c384-11eb-8993-0e0ac0db305f_knafeh_with_peanuts.jpeg",
            "Kunafah With Peanuts","3.50","0","7","medium","")))
    }

    fun initMenu(){
        var nv = findViewById<View>(R.id.nv) as NavigationView
        nv.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.nav_menu ->{}
                R.id.nav_cart -> {startActivity(Intent(this@ProductListActivity,CartActivity::class.java ))}
                R.id.nav_log_out -> {
                    finish()
                }

                else -> return@OnNavigationItemSelectedListener true
            }
            drawerView?.closeDrawers()
            super.onOptionsItemSelected(item)


        })
    }
    private fun showAlertDialog(product: Product){


        var alertDialog = AlertDialog.Builder(this);
        alertDialog.setTitle("Choose Size")
        var selectedSize = 1
        var smallPrice = product.price?.toDouble()

        var mediumPrice = product.price?.toDouble()
        if(product.menuId == "0" || product.menuId == "1" || product.menuId == "7"|| product.menuId == "4"){
            mediumPrice = mediumPrice?.plus(6.00)
        }else if(product.menuId == "3"){
            mediumPrice = mediumPrice?.plus(7.50)
        }else if(product.menuId == "2"){
            mediumPrice = mediumPrice?.plus(11.00)
        }else if(product.menuId == "5" || product.menuId == "6"){
            mediumPrice = mediumPrice?.plus(8.00)
        }

        var largePrice = product.price?.toDouble()
        if(product.menuId == "0" || product.menuId == "1"
            || product.menuId == "5"|| product.menuId == "6"|| product.menuId == "7"){
            largePrice = largePrice?.plus(13.00)
        }else if(product.menuId == "2"){
            largePrice = largePrice?.plus(16.00)
            }else if(product.menuId == "3"){
            largePrice = largePrice?.plus(13.50)
        }else if(product.menuId == "4") {
            largePrice = largePrice?.plus(12.00)
        }


        var items = arrayOf("small €${product.price}","medium €$mediumPrice","large €$largePrice")


        var checkedItem = 1

        alertDialog.setSingleChoiceItems(items, checkedItem, object: DialogInterface.OnClickListener{

            override fun onClick( dialog: DialogInterface?,which: Int ) {
                when (which) {
                  0 ->{

                      selectedSize = 0
                      product.size = "small"
                   }
                    1 ->{

                        selectedSize = 1
                        product.size = "medium"
                    }
                    2 ->{

                        selectedSize = 2
                        product.size = "large"
                    }
                }
            }

          })
        alertDialog.setPositiveButton("OK",object: DialogInterface.OnClickListener{
            val intent = Intent(this@ProductListActivity,ProductDetailActivity::class.java)
            val extraChoices = arrayOf("Extra Cheese (€1.00)","Ice-Cream (€1.00)")
            var checkedChoices = booleanArrayOf(false,false)

            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(product.menuId == "0" || product.menuId == "1"|| product.menuId == "2"
                    || product.menuId == "3"){
                    var alertDialog = AlertDialog.Builder(this@ProductListActivity);
                    alertDialog.setTitle("Add Extras ?")
                    if(selectedSize == 0){
                        chosenLastPrice += smallPrice!!

                        alertDialog.setMultiChoiceItems(extraChoices,checkedChoices, object : DialogInterface.OnMultiChoiceClickListener{
                            override fun onClick(p0: DialogInterface?, index: Int, checked: Boolean) {
                                checkedChoices[index] = checked
                            }

                        })
                        alertDialog.setPositiveButton("OK",object: DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, index: Int) {

                                Toast.makeText(this@ProductListActivity,"${extraChoices[1]} ${checkedChoices[1]}", Toast.LENGTH_LONG).show()
                                if(checkedChoices[0]) {
                                    chosenLastPrice += 1.00
                                    product.extra = "${product.extra}/ extra cheese €1.00"
                                }
                                if(checkedChoices[1]) {
                                    chosenLastPrice += 1.00
                                    product.extra = "${product.extra}/ extra Ice-Cream €1.00"
                                }

                                intent.putExtra("product",product)
                                intent.putExtra("productTotalPrice",chosenLastPrice)
                                startActivity(intent)
                            }

                        }).setNegativeButton("Cancel",object: DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                intent.putExtra("product",product)
                                intent.putExtra("productTotalPrice",chosenLastPrice)
                                startActivity(intent)
                            }

                        })
                    }else{
                        if(selectedSize == 1){
                            chosenLastPrice += mediumPrice!!
                        }else{
                            if(selectedSize == 2){
                                chosenLastPrice += largePrice!!
                            }
                        }
                        alertDialog.setMultiChoiceItems(extraChoices,checkedChoices, object : DialogInterface.OnMultiChoiceClickListener{
                            override fun onClick(p0: DialogInterface?, index: Int, checked: Boolean) {
                                checkedChoices[index] = checked
                            }

                        })
                        alertDialog.setPositiveButton("Ok",object: DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {

                                if(checkedChoices[0]) {
                                    chosenLastPrice += 4.00
                                    product.extra = "${product.extra}/ extra cheese €4.00"
                                }
                                if(checkedChoices[1]) {
                                    chosenLastPrice += 4.00
                                    product.extra = "${product.extra}/ extra Ice-Cream €4.00"
                                }

                              //  product.extraCheese = "extra cheese €4.00"
                                intent.putExtra("product",product)
                                intent.putExtra("productTotalPrice",chosenLastPrice)
                                startActivity(intent)
                            }

                        }).setNegativeButton("Cancel",object: DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                intent.putExtra("product",product)
                                intent.putExtra("productTotalPrice",chosenLastPrice)
                                startActivity(intent)
                            }

                        })

                    }


                    var alert: AlertDialog  = alertDialog.create();
                    alert.setCanceledOnTouchOutside(true);
                    alert.show()
                }else{
                    when(selectedSize){
                        0 -> {
                            chosenLastPrice += smallPrice!!
                        }
                        1 -> {
                            chosenLastPrice += mediumPrice!!
                        }
                        2 -> {
                            chosenLastPrice += largePrice!!
                        }
                    }
                    intent.putExtra("product",product)
                    intent.putExtra("productTotalPrice",chosenLastPrice)
                    startActivity(intent)
                }

            }
        })
            .setNegativeButton("Cancel", object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    chosenLastPrice = 0.0
                }

            })


        var alert: AlertDialog  = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show()

    }


    class ProductItem(val product :Product): Item<GroupieViewHolder>(){
        override fun bind(p0: GroupieViewHolder, p1: Int) {
            p0.itemView.findViewById<TextView>(R.id.txtNameProduct).text = product.name
            p0.itemView.findViewById<TextView>(R.id.txtPriceProductCart).text = product.price

            var image = p0.itemView.findViewById<ImageView>(R.id.imgProduct)
            Picasso.get().load(product.image).into(image, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(p0: Exception?) {

                }

            })


        }

        override fun getLayout(): Int {
           return R.layout.product_item
        }
    }


    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }



    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
        Toast.makeText(this,"clicked", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))


    }
}
