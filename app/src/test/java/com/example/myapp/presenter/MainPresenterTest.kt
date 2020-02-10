package com.example.myapp.presenter

import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.User
import com.example.myapp.utils.StubRunner
import com.example.myapp.view.MainActivity
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.AdditionalMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MainPresenterTest {

    @Mock
    lateinit var mPresenter: MainPresenter

    @Mock
    lateinit var mView: MainActivity

    lateinit var mModel: Model

    @Before
    fun setUp() {
        mView = mock(MainActivity::class.java)
        mModel = mock(Model::class.java)


        Mockito.`when`(mModel.showUsers(42)).thenReturn(arrayListOf(User("diana", 0)))
        Mockito.`when`(mModel.showProducts(42)).thenReturn(arrayListOf(Product("name", "price", "count")))
        mPresenter = MainPresenter(mModel, StubRunner())

    }

    @Test
    fun showUsers() {

        mPresenter.attachView(mView)
        mPresenter.showData(42)
        Mockito.verify(mView).showData(arrayListOf(Product("name", "price", "count")), arrayListOf(User("diana", 0)))

//        mPresenter.showUsers(42)
//        Mockito.verify(mView).getUsers(arrayListOf(User("diana", 0)))

    }

    @Test
    fun attachAndDetach() {

        mPresenter.attachView(mView)
        mPresenter.showData(42)
        Mockito.verify(mView).showData(arrayListOf(Product("name", "price", "count")), arrayListOf(User("diana", 0)))


        mPresenter.detachView()
        mPresenter.showData(42)
        Mockito.verify(mView).showData(arrayListOf(Product("name", "price", "count")), arrayListOf(User("diana", 0)))
        Mockito.verifyNoMoreInteractions(mView)
    }

    @Test
    fun addOneMoreCheck() {
        mPresenter.attachView(mView)
        mPresenter.addOneMoreCheck("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")
    }
}