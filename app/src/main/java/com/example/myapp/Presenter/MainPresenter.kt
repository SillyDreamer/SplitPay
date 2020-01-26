package com.example.myapp.Presenter

import android.content.Context
import android.widget.CheckBox
import com.example.myapp.Contract.MainContract
import com.example.myapp.Model.Model
import com.example.myapp.Model.Product
import com.example.myapp.Model.User

class MainPresenter(val context : Context): MainContract.presenter {
    override fun addMoneyFromUser(users : ArrayList<User>, checkMap : HashMap<Pair<String, String>, ArrayList<CheckBox>>) {
        val money = arrayListOf<Int>()
        val user = arrayListOf<String>()
        for (x in users) {
            user.add(x.name)
            money.add(0)
        }
        for ((k , v) in checkMap) {
            var i = 0
            for (x in v) {
                if (x.isChecked)
                    i++
            }
            for (x in v) {
                if (x.isChecked) {
                    money[user.indexOf(x.text.toString())] = money[user.indexOf(x.text.toString())] +  k.second.toInt() / i
                }
            }
        }
        for (i in 0..(user.size - 1)) {
            model.updateUser(user[i], money[i], i+1)
        }

    }

    private val model = Model(context)

    override fun showUsers(): ArrayList<User> = model.showUsers()

    override fun showProducts(): ArrayList<Product>  = model.showProducts()


}