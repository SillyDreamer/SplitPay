package com.example.myapp.presenter

import com.example.myapp.model.Model
import com.example.myapp.model.Repository
import com.example.myapp.utils.StubRunner
import com.example.myapp.view.AddUserActivity
import com.example.myapp.view.QrActivity
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.mock

class QrPresenterTest {

    @Mock
    lateinit var mPresenter: QrPresenter

    @Mock
    lateinit var mView: QrActivity

    lateinit var mModel: Model

    lateinit var mRep : Repository

    @Before
    fun setUp() {
        mView = mock(QrActivity::class.java)
        mModel = mock(Model::class.java)
        mRep = mock(Repository::class.java)

        mPresenter = QrPresenter(mModel, StubRunner(), mRep)
    }

    @Test
    fun onButtonWasClicked() {
        mPresenter.onButtonWasClicked("t=20191123T1821&s=1496.64&fn=9280440300065001&i=57638&fp=3805453234&n=1")
    }
}