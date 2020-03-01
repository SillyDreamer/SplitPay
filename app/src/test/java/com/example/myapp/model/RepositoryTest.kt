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
        //mRepository.parseQr(null)
        //mRepository.loadMessage(null)
    }

}