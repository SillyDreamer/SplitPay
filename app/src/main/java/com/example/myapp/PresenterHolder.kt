package com.example.myapp

import com.example.myapp.presenter.*

interface PresenterHolder {
    fun getAddUserPresenter(): AddUserPresenter
    fun getMainPresenter(): MainPresenter
    fun getResultPresenter(): ResultPresenter
    fun getPreviousCheckPresenter(): PreviousCheckPresenter
}