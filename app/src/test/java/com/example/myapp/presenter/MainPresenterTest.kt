package com.example.myapp.presenter

import android.widget.CheckBox
import com.example.myapp.model.Model
import com.example.myapp.model.Product
import com.example.myapp.model.Repository
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

    lateinit var mPresenter: MainPresenter

    @Mock
    lateinit var mView: MainActivity

    @Mock
    lateinit var mModel: Model

    @Mock
    lateinit var mRep: Repository

    @Before
    fun setUp() {
        mView = mock(MainActivity::class.java)
        mModel = mock(Model::class.java)
        mRep = mock(Repository::class.java)


        Mockito.`when`(mModel.showUsers(42)).thenReturn(arrayListOf(User("diana", 0, 0.0)))
        Mockito.`when`(mModel.showProducts(42))
            .thenReturn(arrayListOf(Product("name", "price", "count")))
        Mockito.`when`(mRep.parseQr("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1"))
            .thenReturn(
                Pair(
                    arrayListOf(Product("Спагетти карбонара", "1800", "1")), "2019-11-23T18:21:00"
                )
            )

        mPresenter = MainPresenter(mModel, StubRunner(), mRep)

    }

    @Test
    fun showUsers() {

        mPresenter.attachView(mView)
        mPresenter.showData(42)
        Mockito.verify(mView).showData(
            arrayListOf(Product("name", "price", "count")),
            arrayListOf(User("diana", 0, 0.0))
        )

    }

    @Test
    fun attachAndDetach() {

        mPresenter.attachView(mView)
        mPresenter.showData(42)
        Mockito.verify(mView).showData(
            arrayListOf(Product("name", "price", "count")),
            arrayListOf(User("diana", 0, 0.0))
        )


        mPresenter.detachView()
        mPresenter.showData(42)
        Mockito.verify(mView).showData(
            arrayListOf(Product("name", "price", "count")),
            arrayListOf(User("diana", 0, 0.0))
        )
        Mockito.verifyNoMoreInteractions(mView)
    }

    @Test
    fun addOneMoreCheck() {
        mPresenter.attachView(mView)
        mPresenter.showData(42)
        mPresenter.addOneMoreCheck("t=20200129T1400&s=180.00&fn=9284000100287274&i=24351&fp=4163484040&n=1")
        mPresenter.addOneMoreCheck("123")
    }

    @Test
    fun addMoney() {

        mPresenter.attachView(mView)
        mPresenter.addMoneyFromUser(arrayListOf("user"), arrayListOf(21), arrayListOf(42L), 42)
    }
}