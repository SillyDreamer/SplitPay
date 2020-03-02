package com.example.myapp.contract

import com.example.myapp.view.ScanActivity

interface ScanContract {
    interface View{}

    interface Presenter {
        fun onButtonWasClicked(qrResult: String)
        fun parseDate(date : String) : String
        fun attachView(view : ScanActivity)
        fun detachView()
    }
}
