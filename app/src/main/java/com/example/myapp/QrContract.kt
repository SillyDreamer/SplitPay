package com.example.myapp

interface QrContract {
    interface View {

    }

    interface Presenter {
        fun onButtonWasClicked(qrResult : String?)

    }
}