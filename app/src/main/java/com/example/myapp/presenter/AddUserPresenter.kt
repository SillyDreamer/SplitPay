package com.example.myapp.presenter

import android.content.Context
import com.example.myapp.contract.AddUserContract
import com.example.myapp.model.Model

class AddUserPresenter(var context: Context) : AddUserContract.Presenter {
    override fun addButton(name: String, money : Int) {
        val model: Model = Model(context)

        val runnable = Runnable { model.addToDBUser(name, money) }
        Thread(runnable).start()
    }
}