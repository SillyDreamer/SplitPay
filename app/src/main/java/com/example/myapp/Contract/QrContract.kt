package com.example.myapp.Contract

interface QrContract {
    interface View {

    }

    interface Presenter {
        fun onButtonWasClicked(qrResult : String?)

    }
}