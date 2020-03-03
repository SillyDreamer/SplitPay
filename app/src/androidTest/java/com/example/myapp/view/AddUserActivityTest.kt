package com.example.myapp.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.myapp.R
import org.junit.Rule
import org.junit.Test

class AddUserActivityTest {


    @get:Rule
    val activityRule = ActivityTestRule(AddUserActivity::class.java)

    @Test
    fun test() {
        onView(withId(R.id.editText)).perform(typeText("hello"))
        onView(withId(R.id.add)).perform(click())
        onView(withId(R.id.editText)).perform(typeText("world"))
        onView(withId(R.id.add)).perform(click())
        onView(withId(R.id.button_next)).perform(click())
    }
}