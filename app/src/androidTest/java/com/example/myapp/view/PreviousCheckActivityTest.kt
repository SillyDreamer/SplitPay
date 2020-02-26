package com.example.myapp.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.myapp.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PreviousCheckActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(PreviousCheckActivity::class.java)

    @Test
    fun changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.fab))
    }

}