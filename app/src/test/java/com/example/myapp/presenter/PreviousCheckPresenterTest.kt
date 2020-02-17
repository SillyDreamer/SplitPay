package com.example.myapp.presenter

import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
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
    lateinit var mView: PreviousCheckActivity

    @Mock
    lateinit var mModel: Model

    lateinit var mPresenter: PreviousCheckPresenter

    lateinit var mRep: Repository

    @Before
    fun setUp() {

        mView = mock(PreviousCheckActivity::class.java)
        mModel = mock(Model::class.java)
        mRep = mock(Repository::class.java)

        Mockito.`when`(mModel.showChecks()).thenReturn(arrayListOf(Triple("42", "name", "date")))
        Mockito.`when`(mRep.loadMessage("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1"))
            .thenReturn(
                Pair(
                    arrayListOf(Product("Спагетти карбонара", "1800", "1")), "date"
                )
            )

        mPresenter = PreviousCheckPresenter(mModel, StubRunner(), mRep)
    }

    @Test
    fun showChecks() {
        mPresenter.attachView(mView)
        mPresenter.showChecks()
        Mockito.verify(mView).showChecks(arrayListOf(Triple("42", "name", "date")))
    }

    @Test
    fun buttonClick() {
        mPresenter.attachView(mView)
        mPresenter.onButtonWasClicked("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")
        mPresenter.onButtonWasClicked("123")
    }

    @Test
    fun update() {
        mPresenter.attachView(mView)
        mPresenter.updateCheck("name", "date", 42)
    }

    @Test
    fun attachView() {
        mPresenter.attachView(mView)
        mPresenter.showChecks()
        Mockito.verify(mView).showChecks(arrayListOf(Triple("42", "name", "date")))

        mPresenter.detachView()
        mPresenter.showChecks()
        Mockito.verify(mView).showChecks(arrayListOf(Triple("42", "name", "date")))
        Mockito.verifyNoMoreInteractions(mView)

    }
}