package com.example.myapp.presenter

import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
import com.example.myapp.utils.StubRunner
import com.example.myapp.view.AddUserActivity
import com.example.myapp.view.QrActivity
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
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

        Mockito.`when`(mRep.loadMessage("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")).thenReturn(Pair(
            arrayListOf(Product("Спагетти карбонара", "1800", "1")), "date"))

        mPresenter = QrPresenter(mModel, StubRunner(), mRep)
    }

    @Test
    fun onButtonWasClicked() {
        mPresenter.onButtonWasClicked("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")
    }
}