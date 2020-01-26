package com.example.myapp

import android.content.Context

class ResultPresenter(context : Context): ResultContract.presenter {

    private val model = Model(context)
    override fun showUsers(): ArrayList<User> {
       return  model.showUsers()
    }
}