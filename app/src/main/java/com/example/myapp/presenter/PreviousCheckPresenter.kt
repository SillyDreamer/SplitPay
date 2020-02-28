package com.example.myapp.presenter

import com.example.myapp.contract.PreviousCheckContract
import com.example.myapp.model.Model
import com.example.myapp.model.Repository
import com.example.myapp.utils.Runner
import com.example.myapp.view.PreviousCheckActivity

class PreviousCheckPresenter(val model: Model, val runner: Runner, val rep: Repository) :
    PreviousCheckContract.Presenter {

    var mView: PreviousCheckActivity? = null

    override fun showChecks() {
        runner.runInBackground(Runnable {
            val arr = model.showChecks()
            runner.runOnMain(Runnable {
                mView?.showChecks(arr)
            })
        })
    }

    override fun updateCheck(name: String, date: String, id: Long) {
        runner.runInBackground(Runnable {
            model.updateCheck(name, date, id)
        })
    }

    override fun attachView(view: PreviousCheckActivity) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}