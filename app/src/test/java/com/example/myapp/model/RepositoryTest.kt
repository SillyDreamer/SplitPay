package com.example.myapp.model

import org.json.JSONObject
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

class RepositoryTest {

    lateinit var mRepository: Repository

    @Mock
    lateinit var mJson: JSONObject

    @Before
    fun setUp() {
        mJson = mock(JSONObject::class.java)

        mRepository = Repository()
    }

    @Test
    fun testParser() {
        mRepository.parseQr("t=20200301T2237&s=822.20&fn=9289000100514688&i=27009&fp=2251170559&n=1")
        //mRepository.loadMessage(null)
    }

}