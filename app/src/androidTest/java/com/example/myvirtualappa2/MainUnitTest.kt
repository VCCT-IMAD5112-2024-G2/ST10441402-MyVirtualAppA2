package com.example.myvirtualappa2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityTestRule<T> {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private fun ActivityTestRule(java: Class<MainActivity>): ActivityTestRule<MainActivity> {
        TODO("Not yet implemented")
    }

    @Test
    fun testStartButtonClicked() {
        // Click on the start button
        onView(withId(R.id.startButton)).perform(click())

        // You can add more assertions here if needed
        // For example, you can check if the SecondScreenActivity is displayed
        // onView(withId(R.id.secondScreen)).check(matches(isDisplayed()))
    }
}


