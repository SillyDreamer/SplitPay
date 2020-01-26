package com.example.myapp.Presenter

import android.content.Context
import com.example.myapp.Contract.AddUserContract
import com.example.myapp.Model.Model

class AddUserPresenter(var context: Context) : AddUserContract.Presenter {
    override fun addButton(name: String, money : Int) {
        var model: Model = Model(context)
        model.addToDBUser(name, money)
    }
}