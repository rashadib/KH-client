package io.github.kunafahhouse.takeaway.Model

class Order() {

    lateinit var extra: String
    lateinit var productName: String
    lateinit var quantity: String
    lateinit var price: String
    lateinit var size: String


    constructor(extra: String, productName: String, quantity: String
                , price: String, size: String) : this() {

        this.extra = extra
        this.productName = productName
        this.quantity = quantity
        this.price = price
        this.size = size

    }
}