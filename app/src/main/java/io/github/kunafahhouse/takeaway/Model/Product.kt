package io.github.kunafahhouse.takeaway.Model
import java.io.Serializable

class Product(): Serializable {

    var name: String? = null
    var image: String? = null
    var description: String? = null
    var price: String? = null
    var discount: String? = null
    var menuId: String? = null
    var size: String? = null
    var extra: String? = null


    constructor(name: String, image: String, description: String,
                price: String, discount: String, menuId: String, size: String, extra: String) : this() {
        this.name = name
        this.image = image
        this.description = description
        this.price = price
        this.discount = discount
        this.menuId = menuId
        this.size = size
        this.extra = extra

    }

}