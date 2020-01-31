package com.example.myapp.presenter

import android.content.Context
import android.os.AsyncTask
import android.widget.CheckBox
import com.example.myapp.contract.MainContract
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.User

class MainPresenter(val context : Context): MainContract.presenter {
    override fun addMoneyFromUser(users : ArrayList<User>, checkMap : HashMap<Pair<String, String>, ArrayList<CheckBox>>) {

        val runnable = Runnable {
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
            for (i in 0 until user.size) {
                model.updateUser(user[i], money[i], i+1)
            } }

        Thread(runnable).start()

    }

    private val model = Model(context)

    override fun showUsers(): ArrayList<User> {
        val task = UserAsyncTask().execute()
        return task.get()
    }


    override fun showProducts(): ArrayList<Product> {
        val task = ProductAsyncTask().execute()
        return task.get()
    }

    inner class ProductAsyncTask : AsyncTask<String, String, ArrayList<Product>>() {
        override fun doInBackground(vararg params: String?): ArrayList<Product> {
            return model.showProducts()
        }

    }


    inner class UserAsyncTask : AsyncTask<String, String, ArrayList<User>>() {
        override fun doInBackground(vararg params: String?): ArrayList<User> {
            return model.showUsers()
        }

    }


}