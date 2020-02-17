package com.example.myapp.model

import android.database.Cursor
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ModelTest {

    @Mock
    lateinit var mDbOpenHelper: DBOpenHelper

    @Mock
    lateinit var mCursor: Cursor

    lateinit var mModel: Model

    @Before
    fun setUp() {
        mDbOpenHelper = mock(DBOpenHelper::class.java)
        mCursor = mock(Cursor::class.java)

        Mockito.`when`(mCursor.count).thenReturn(42)
        Mockito.`when`(mDbOpenHelper.getChecks()).thenReturn(mCursor)
        Mockito.`when`(mDbOpenHelper.getProducts()).thenReturn(mCursor)
        Mockito.`when`(mDbOpenHelper.getUsers()).thenReturn(mCursor)


        mModel = Model(mDbOpenHelper)
        val user = User("user", 0, 42, 22)
        user.id = 42
    }

    @Test
    fun showCheckId() {
        mModel.showCheckId()
        Mockito.verify(mCursor).moveToNext()


    }

    @Test
    fun showChecks() {
        mModel.showChecks()
        Mockito.verify(mCursor).moveToNext()

    }

    @Test
    fun showUsers() {
        mModel.showUsers(42)
        Mockito.verify(mCursor).moveToNext()

    }

    @Test
    fun showProducts() {
        mModel.showProducts(42)
        Mockito.verify(mCursor).moveToNext()

    }

    @Test
    fun addToDB() {
        mModel.addToDBUser("user")
        Mockito.verify(mDbOpenHelper).addUser("user")
        mModel.addToDBCheck("name", "date")
        Mockito.verify(mDbOpenHelper).addCheck("name", "date")
        mModel.addToDBProduct(Product("name", "price", "count"))
        Mockito.verify(mDbOpenHelper).add(Product("name", "price", "count"))
    }

    @Test
    fun update() {
        mModel.updateUser("name", 0, 0, 42, 42)
        Mockito.verify(mDbOpenHelper).updateUser("name", 0, 0, 42, 42)
        mModel.updateCheck("name", "date", 1)
        Mockito.verify(mDbOpenHelper).updateCheck("name", "date", 1)
    }
}