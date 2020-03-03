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
        mRepository.parseQr("t=20200301T2243&s=214.90&fn=9289000100514688&i=27014&fp=185672516&n=1")
    }

}