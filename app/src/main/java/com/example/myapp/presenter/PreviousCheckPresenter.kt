package com.example.myapp.presenter

import com.example.myapp.contract.PreviousCheckContract
import com.example.myapp.model.Model

class PreviousCheckPresenter(val model : Model) : PreviousCheckContract.Presenter {


    override fun showChecks(): ArrayList<Pair<Long, String>> {
       return  model.showChecks()
    }
}