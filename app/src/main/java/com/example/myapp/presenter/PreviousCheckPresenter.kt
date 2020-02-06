package com.example.myapp.presenter

import android.content.Context
import com.example.myapp.contract.PreviousCheckContract
import com.example.myapp.model.Model

class PreviousCheckPresenter(context : Context) : PreviousCheckContract.Presenter {

    private val model = Model(context)

    override fun showChecks(): ArrayList<Pair<Long, String>> {
       return  model.showChecks()
    }
}