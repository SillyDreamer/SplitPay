package com.example.myapp.model

data class User(var name : String, var money : Int) {
    var id : Long = 0
    constructor(name : String, money : Int, id : Long) : this(name, money) {
        this.id = id
    }
}