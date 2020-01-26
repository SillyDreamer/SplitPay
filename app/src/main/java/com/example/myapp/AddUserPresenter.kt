package com.example.myapp

import android.content.Context

class AddUserPresenter(var context: Context) : AddUserContract.Presenter {
    override fun addButton(name: String, money : Int) {
        var model: Model = Model(context)
        model.addToDBUser(name, money)
    }
}