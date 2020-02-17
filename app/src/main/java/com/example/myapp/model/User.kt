package com.example.myapp.model

data class User(var name: String, var money: Int, var paid: Int) {
    var id: Long = 0

    constructor(name: String, money: Int, paid: Int, id: Long) : this(name, money, paid) {
        this.id = id
    }
}