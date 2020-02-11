package com.example.myapp.presenter

import com.example.myapp.model.Model
import com.example.myapp.utils.StubRunner
import com.example.myapp.view.PreviousCheckActivity
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

class PreviousCheckPresenterTest {

    @Mock
    lateinit var mView : PreviousCheckActivity

    @Mock
    lateinit var mModel : Model

    lateinit var mPresenter : PreviousCheckPresenter

    @Before
    fun setUp() {

        mView = mock(PreviousCheckActivity::class.java)
        mModel = mock(Model::class.java)

        Mockito.`when`(mModel.showChecks()).thenReturn(arrayListOf(Pair(42L, "date")))

        mPresenter = PreviousCheckPresenter(mModel, StubRunner())
    }

    @Test
    fun showChecks() {
        mPresenter.attachView(mView)
        mPresenter.showChecks()
        Mockito.verify(mView).showChecks(arrayListOf(Pair(42L, "date")))
    }

    @Test
    fun attachView() {
        mPresenter.attachView(mView)
        mPresenter.showChecks()
        Mockito.verify(mView).showChecks(arrayListOf(Pair(42L, "date")))

        mPresenter.detachView()
        mPresenter.showChecks()
        Mockito.verify(mView).showChecks(arrayListOf(Pair(42L, "date")))
        Mockito.verifyNoMoreInteractions(mView)

    }
}