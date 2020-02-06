package com.example.myapp.presenter

import com.example.myapp.contract.AddUserContract
import com.example.myapp.model.Model

class AddUserPresenter(var model :  Model) : AddUserContract.Presenter {


    override fun addButton(name: String, money : Int) {

        val runnable = Runnable { model.addToDBUser(name, money) }
        Thread(runnable).start()
    }

    fun lastCheckId() = model.showCheckId()
}