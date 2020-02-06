package com.example.myapp.presenter

import android.content.Context
import com.example.myapp.contract.ResultContract
import com.example.myapp.model.Model
import com.example.myapp.model.User

class ResultPresenter(val model : Model): ResultContract.presenter {

    override fun showUsers(check_id : Long): ArrayList<User> = model.showUsers(check_id)
}