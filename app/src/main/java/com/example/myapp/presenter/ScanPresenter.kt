package com.example.myapp.presenter

import com.example.myapp.R
import com.example.myapp.contract.ScanContract
import com.example.myapp.model.Model
import com.example.myapp.model.Repository
import com.example.myapp.utils.Runner
import com.example.myapp.view.ScanActivity

class ScanPresenter(
    private val model: Model,
    private val runner: Runner,
    private val rep: Repository
) : ScanContract.Presenter {

    var mView: ScanActivity? = null

    override fun onButtonWasClicked(qrResult: String) {
        if (!qrResult.matches(Regex("t=[0-9]*T[0-9]*&s=[0-9|.]*&fn=[0-9]*&i=[0-9]*&fp=[0-9]*&n=[0-9]"))) {
            mView?.wrongQr()
        } else {
            runner.runInBackground(Runnable {
                val arr = rep.parseQr(qrResult)
                val parse = arr.first
                val date = parseDate(arr.second)
                val name = "чек " + model.showCheckId()
                model.addToDBCheck(name, date)
                for (product in parse) {
                    model.addToDBProduct(product)
                }
            })
            mView?.buttonClick()
        }
    }

    override fun parseDate(date: String): String {
        var date2 = date.substring(8, 10) + " "
        when {
            date.substring(5, 7) == "01" -> date2 += mView?.getString(R.string.januare)
            date.substring(5, 7) == "02" -> date2 += mView?.getString(R.string.februare)
            date.substring(5, 7) == "03" -> date2 += mView?.getString(R.string.march)
            date.substring(5, 7) == "04" -> date2 += mView?.getString(R.string.april)
            date.substring(5, 7) == "05" -> date2 += mView?.getString(R.string.may)
            date.substring(5, 7) == "06" -> date2 += mView?.getString(R.string.june)
            date.substring(5, 7) == "07" -> date2 += mView?.getString(R.string.jule)
            date.substring(5, 7) == "08" -> date2 += mView?.getString(R.string.august)
            date.substring(5, 7) == "09" -> date2 += mView?.getString(R.string.september)
            date.substring(5, 7) == "10" -> date2 += mView?.getString(R.string.october)
            date.substring(5, 7) == "12" -> date2 += mView?.getString(R.string.december)
            date.substring(5, 7) == "11" -> date2 += mView?.getString(R.string.november)
        }
        date2 += " " + date.substring(0, 4) + " года"
        return date2
    }

    override fun attachView(view: ScanActivity) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}