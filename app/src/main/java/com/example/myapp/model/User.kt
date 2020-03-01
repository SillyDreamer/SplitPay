package com.example.myapp.model

data class User(var name: String, var money: Int, var paid: Double) {
    var id: Long = 0

    constructor(name: String, money: Int, paid: Double, id: Long) : this(name, money, paid) {
        this.id = id
    }
}