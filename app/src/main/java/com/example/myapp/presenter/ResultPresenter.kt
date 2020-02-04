package com.example.myapp.presenter

import android.content.Context
import com.example.myapp.contract.ResultContract
import com.example.myapp.model.Model
import com.example.myapp.model.User

class ResultPresenter(context : Context): ResultContract.presenter {

    private val model = Model(context)
    override fun showUsers(check_id : Long): ArrayList<User> = model.showUsers(check_id)
}