package com.example.myvirtualappa2

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SecondScreenActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<SecondScreenActivity> = ActivityTestRule(SecondScreenActivity::class.java)

    @Test
    fun testFeedButtonClicked() {
        // Click on the feed button
        onView(withId(R.id.feedButton)).perform(click())

        // You can add assertions here to verify the expected behavior
    }

    @Test
    fun testCleanButtonClicked() {
        // Click on the clean button
        onView(withId(R.id.cleanButton)).perform(click())

        // You can add assertions here to verify the expected behavior
    }

    @Test
    fun testPlayButtonClicked() {
        // Click on the play button
        onView(withId(R.id.playButton)).perform(click())

        // You can add assertions here to verify the expected behavior
    }

    // Add more tests as needed
}
