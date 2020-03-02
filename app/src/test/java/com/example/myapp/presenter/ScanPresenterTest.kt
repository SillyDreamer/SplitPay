package com.example.myapp.presenter

import com.example.myapp.R
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
import com.example.myapp.utils.StubRunner
import com.example.myapp.view.ScanActivity
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyNoMoreInteractions

class ScanPresenterTest {

    lateinit var mPresenter: ScanPresenter

    @Mock
    lateinit var mView: ScanActivity

    @Mock
    lateinit var mRep: Repository

    @Mock
    lateinit var mModel: Model

    @Before
    fun setUp() {
        mView = mock(ScanActivity::class.java)
        mRep = mock(Repository::class.java)
        mModel = mock(Model::class.java)

        Mockito.`when`(mRep.parseQr("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1"))
            .thenReturn(
                Pair(
                    arrayListOf(Product("Спагетти карбонара", "1800", "1")), "2019-11-23T18:21:00"
                )
            )

        mPresenter = ScanPresenter(mModel, StubRunner(), mRep)

    }

    @Test
    fun buttonClick() {
        mPresenter.attachView(mView)
        mPresenter.onButtonWasClicked("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")
        Mockito.verify(mView).buttonClick()
        mPresenter.onButtonWasClicked("123")
        Mockito.verify(mView).wrongQr()
    }

    @Test
    fun attachAndDetach() {
        mPresenter.attachView(mView)
        mPresenter.onButtonWasClicked("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")
        Mockito.verify(mView).buttonClick()

        mPresenter.detachView()
        mPresenter.onButtonWasClicked("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")
        Mockito.verify(mView).buttonClick()
    }
}