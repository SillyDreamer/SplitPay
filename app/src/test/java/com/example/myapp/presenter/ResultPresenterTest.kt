package com.example.myapp.presenter

import com.example.myapp.model.Model
import com.example.myapp.model.User
import com.example.myapp.utils.StubRunner
import com.example.myapp.view.ResultActivity
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ResultPresenterTest {

    @Mock
    lateinit var mView: ResultActivity

    @Mock
    lateinit var mModel: Model

    lateinit var mPresenter: ResultPresenter

    @Before
    fun setUp() {

        mView = mock(ResultActivity::class.java)
        mModel = mock(Model::class.java)

        Mockito.`when`(mModel.showUsers(42)).thenReturn(arrayListOf(User("diana", 0, 0.0)))

        mPresenter = ResultPresenter(mModel, StubRunner())
    }

    @Test
    fun showUsers() {
        mPresenter.attachView(mView)
        mPresenter.showUsers(42)
        Mockito.verify(mView).showUsers(arrayListOf(User("diana", 0, 0.0)))
    }

    @Test
    fun update() {
        mPresenter.attachView(mView)
        mPresenter.updateUser("name", 200, 100.0, 42, 21)
    }

    @Test
    fun attachAndDetach() {

        mPresenter.attachView(mView)
        mPresenter.showUsers(42)
        Mockito.verify(mView).showUsers(arrayListOf(User("diana", 0, 0.0)))

        mPresenter.detachView()
        mPresenter.showUsers(42)
        Mockito.verify(mView).showUsers(arrayListOf(User("diana", 0, 0.0)))
        Mockito.verifyNoMoreInteractions(mView)
    }
}