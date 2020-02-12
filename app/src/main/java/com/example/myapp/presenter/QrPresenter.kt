package com.example.myapp.presenter


import com.example.myapp.contract.QrContract
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
import com.example.myapp.utils.Runner
import com.example.myapp.view.QrActivity
import org.json.JSONObject
import java.util.regex.Pattern



class QrPresenter(val model : Model, val runner : Runner, val rep : Repository) : QrContract.Presenter {

    override fun onButtonWasClicked(qrResult: String?) {

        runner.runInBackground(Runnable {
            val arr = rep.loadMessage(qrResult)
            val parse = arr.first
            val date = arr.second
            val name = "чек " + (model.showCheckId() + 1)
            model.addToDBCheck(name, date)
            for (product in parse) {
                model.addToDBProduct(product)
            }
        })
    }
}
