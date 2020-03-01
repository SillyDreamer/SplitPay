package com.example.myapp.presenter

import com.example.myapp.contract.ResultContract
import com.example.myapp.model.Model
import com.example.myapp.utils.Runner
import com.example.myapp.view.ResultActivity

class ResultPresenter(val model: Model, val runner: Runner) : ResultContract.Presenter {

    var mView: ResultActivity? = null

    override fun showUsers(check_id: Long) {

        runner.runInBackground(Runnable {
            val arr = model.showUsers(check_id)
            runner.runOnMain(Runnable {
                mView?.showUsers(arr)
            })
        })

    }

    override fun updateUser(name: String, money: Int, paid: Double, id: Long, check_id: Long) {
        runner.runInBackground(Runnable {
            model.updateUser(name, money, paid, id, check_id)
        })
    }

    override fun attachView(view: ResultActivity) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}