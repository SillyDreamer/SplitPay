package com.example.myapp.contract

import com.example.myapp.model.Product
import com.example.myapp.model.User
import com.example.myapp.view.MainActivity

interface MainContract {

    interface View {
        fun showData(pr: ArrayList<Product>, us: ArrayList<User>)
        fun addCheck()
    }

    interface Presenter {
        fun showData(check_id: Long)
        fun addMoneyFromUser(
            users: ArrayList<String>,
            money: ArrayList<Int>,
            id: ArrayList<Long>,
            check_id: Long
        )

        fun addOneMoreCheck(qrResult: String): Boolean
        fun attachView(view: MainActivity)
        fun detachView()
    }
}