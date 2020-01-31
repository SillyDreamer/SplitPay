package com.example.myapp.Presenter

import android.content.Context
import com.example.myapp.Contract.AddUserContract
import com.example.myapp.Model.Model

class AddUserPresenter(var context: Context) : AddUserContract.Presenter {
    override fun addButton(name: String, money : Int) {
        val model: Model = Model(context)

        val runnable = Runnable { model.addToDBUser(name, money) }
        Thread(runnable).start()
    }
}