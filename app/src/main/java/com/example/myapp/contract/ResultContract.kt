package com.example.myapp.contract

import com.example.myapp.model.User
import com.example.myapp.view.ResultActivity

interface ResultContract {
    interface View {
        fun showUsers(array: ArrayList<User>)
    }

    interface Presenter {
        fun showUsers(check_id: Long)
        fun updateUser(name: String, money: Int, paid: Int, id: Long, check_id: Long)
        fun attachView(view: ResultActivity)
        fun detachView()
    }
}