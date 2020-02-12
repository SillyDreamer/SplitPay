package com.example.myapp.presenter

import com.example.myapp.contract.PreviousCheckContract
import com.example.myapp.model.Model
import com.example.myapp.model.Repository
import com.example.myapp.utils.Runner
import com.example.myapp.view.PreviousCheckActivity

class PreviousCheckPresenter(val model : Model, val runner : Runner, val rep : Repository) : PreviousCheckContract.Presenter {

    var mView : PreviousCheckActivity? = null

    fun onButtonWasClicked(qrResult: String?) {

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
    }

    override fun showChecks() {
        runner.runInBackground(Runnable {
            val arr = model.showChecks()
            runner.runOnMain(Runnable {
                mView?.showChecks(arr)
            })
        })
    }

    fun attachView(view : PreviousCheckActivity) {
        mView = view
    }

    fun detachView() {
        mView = null
    }
}