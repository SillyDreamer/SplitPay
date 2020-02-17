package com.example.myapp.presenter

import com.example.myapp.model.Model
import com.example.myapp.utils.StubRunner
import com.example.myapp.view.AddUserActivity
import com.example.myapp.view.MainActivity
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verifyNoMoreInteractions

class AddUserPresenterTest {

    @Mock
    lateinit var mPresenter: AddUserPresenter

    @Mock
    lateinit var mView: AddUserActivity

    lateinit var mModel: Model


    @Before
    fun setUp() {
        mModel = Mockito.mock(Model::class.java)
        mView = Mockito.mock(AddUserActivity::class.java)


        Mockito.`when`(mModel.showCheckId()).thenReturn(42)
        mPresenter = AddUserPresenter(mModel, StubRunner())


    }


    @Test
    fun lastCheckId() {

        val id: Long = 42
        mPresenter.attachView(mView)
        mPresenter.lastCheckId()
        Mockito.verify(mView).showCheckId(Mockito.eq(id))

    }

    @Test
    fun addButton() {
        mPresenter.attachView(mView)
        mPresenter.addButton("diana", 0)
        //Mockito.verify(mModel).addToDBUser(Mockito.eq("diana"), Mockito.eq(0))
    }

    @Test
    fun attachAndDetach() {
        val id: Long = 42

        mPresenter.attachView(mView)
        mPresenter.lastCheckId()
        Mockito.verify(mView).showCheckId(Mockito.eq(id))

        mPresenter.detachView()
        mPresenter.lastCheckId()
        Mockito.verify(mView).showCheckId(Mockito.eq(id))
        verifyNoMoreInteractions(mView)
    }
}