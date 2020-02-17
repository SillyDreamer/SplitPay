package com.example.myapp.model

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

class DBOpenHelperTest {

    lateinit var mDBOpenHelper: DBOpenHelper

    @Mock
    lateinit var mSQLiteDatabase: SQLiteDatabase

    @Mock
    lateinit var mSQLiteOpenHelper: SQLiteOpenHelper

    @Before
    fun setUp() {

        mSQLiteDatabase = mock(SQLiteDatabase::class.java)
        //mSQLiteOpenHelper = mock(SQLiteOpenHelper::class.java)

        mDBOpenHelper = DBOpenHelper(Application(), null)

        //Mockito.`when`(mSQLiteOpenHelper.writableDatabase).thenReturn(mSQLiteDatabase)
    }

    @Test
    fun onCreate() {
        mDBOpenHelper.onCreate(mSQLiteDatabase)
    }

    @Test
    fun onUpgrade() {
        mDBOpenHelper.onUpgrade(mSQLiteDatabase, 1, 2)
    }

    @Test
    fun update() {
        mDBOpenHelper.updateCheck("name", "date", 42)
    }
}