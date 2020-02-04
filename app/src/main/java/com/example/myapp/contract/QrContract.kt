package com.example.myapp.contract

interface QrContract {
    interface View {

    }

    interface Presenter {
        fun onButtonWasClicked(qrResult : String?)
    }
}