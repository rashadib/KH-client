package io.github.kunafahhouse.takeaway.Model

class Request() {

    lateinit var phone: String
    lateinit var name: String
    lateinit var comment: String
    lateinit var total: String
    lateinit var foods: List<Order>

    constructor(
        phone: String,
        name: String,
        comment: String,
        total: String,
        foods: List<Order>) : this() {
        this.phone = phone
        this.name = name
        this.comment = comment
        this.total = total
        this.foods = foods
    }
}