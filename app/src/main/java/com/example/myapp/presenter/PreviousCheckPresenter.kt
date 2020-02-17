package com.example.myapp.presenter

import com.example.myapp.contract.PreviousCheckContract
import com.example.myapp.model.Model
import com.example.myapp.model.Repository
import com.example.myapp.utils.Runner
import com.example.myapp.view.PreviousCheckActivity

class PreviousCheckPresenter(val model: Model, val runner: Runner, val rep: Repository) :
    PreviousCheckContract.Presenter {

    var mView: PreviousCheckActivity? = null

    fun onButtonWasClicked(qrResult: String): Boolean {
        if (!qrResult.matches(Regex("t=[0-9]*T[0-9]*&s=[0-9|.]*&fn=[0-9]*&i=[0-9]*&fp=[0-9]*&n=[0-9]"))) {
            return false
        }
        runner.runInBackground(Runnable {
            val arr = rep.loadMessage(qrResult)
            val parse = arr.first
            val date = arr.second
            val name = "чек " + model.showCheckId()
            model.addToDBCheck(name, date)
            for (product in parse) {
                model.addToDBProduct(product)
            }
        })
        return true
    }

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