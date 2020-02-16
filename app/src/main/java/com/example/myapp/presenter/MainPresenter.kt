package com.example.myapp.presenter

import com.example.myapp.contract.MainContract
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
import com.example.myapp.utils.Runner
import com.example.myapp.view.MainActivity

class MainPresenter(val model : Model, val runner : Runner, val rep : Repository): MainContract.presenter {

    var mView : MainActivity? = null

    override fun addMoneyFromUser(users : ArrayList<String>, money : ArrayList<Int>, id : ArrayList<Long>, check_id : Long) {
        runner.runInBackground(Runnable {
            for (i in 0 until users.size) {
                model.updateUser(users[i], money[i], 0, id[i], check_id)
            }
        })

    }

    override fun showData(check_id : Long) {

        runner.runInBackground(Runnable {
            val product = model.showProducts(check_id)
            val user = model.showUsers(check_id)
            runner.runOnMain(Runnable {if (mView != null) {
                mView?.showData(product, user)
            }  })
        })
    }

    //add one more check

    var parse: ArrayList<Product> = arrayListOf()


    override fun addOneMoreCheck(qrResult: String) : Boolean {
        if (!qrResult.matches(Regex("t=[0-9]*T[0-9]*&s=[0-9|.]*&fn=[0-9]*&i=[0-9]*&fp=[0-9]*&n=[0-9]"))) {
            return false
        }
        runner.runInBackground(Runnable {
            val arr = rep.loadMessage(qrResult)
            parse = arr.first
            for (product in parse) {
                model.addToDBProduct(product)
            }
            runner.runOnMain(Runnable {
                mView?.addCheck()
            })
        })
        return true
    }

    fun attachView(view : MainActivity) {
        mView = view
    }

    fun detachView() {
        mView = null
    }

}