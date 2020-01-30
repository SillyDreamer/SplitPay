package com.example.myapp.Presenter

import android.content.Context
import com.example.myapp.Contract.ResultContract
import com.example.myapp.Model.Model
import com.example.myapp.Model.User

class ResultPresenter(context : Context): ResultContract.presenter {

    private val model = Model(context)
    override fun showUsers(): ArrayList<User> = model.showUsers()
}