package com.example.myapp.presenter

import com.example.myapp.contract.AddUserContract
import com.example.myapp.model.Model
import com.example.myapp.utils.Runner
import com.example.myapp.view.AddUserActivity

class AddUserPresenter(var model: Model, val runner: Runner) : AddUserContract.Presenter {

    var mView: AddUserActivity? = null

    override fun addButton(name: String, money: Int) {
        runner.runInBackground(Runnable { model.addToDBUser(name) })
    }

    override fun attachView(view: AddUserActivity) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    override fun lastCheckId() {
        runner.runInBackground(Runnable {
            val id = model.showCheckId()
            runner.runOnMain(Runnable {
                if (mView != null) {
                    mView?.showCheckId(id)
                }
            })
        })
    }
}