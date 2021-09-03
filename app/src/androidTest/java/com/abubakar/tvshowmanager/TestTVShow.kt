package com.abubakar.tvshowmanager

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test


class TestTVShow {

    @Test
    fun testInsertShow() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btnAddNew))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnShowAll))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnAddNew))
            .perform(click())

        onView(withId(R.id.inputTvShowTitle))
            .check(matches(isDisplayed()))

        onView(withId(R.id.inputTvShowTitle))
            .perform(typeText("Test1"), closeSoftKeyboard())

        onView(withId(R.id.inputSeasons))
            .check(matches(isDisplayed()))

        onView(withId(R.id.releaseDataField))
            .perform(click())

        onView(withId(R.id.datePicker))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnNext))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnNext))
            .perform(click())

        onView(withId(R.id.timePicker))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnDone))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnDone))
            .perform(click())

        onView(withId(R.id.btnSaveTVShow))
            .perform(click())


    }

    @Test
    fun testShowList() {
        //start activity
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btnAddNew))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnShowAll))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnShowAll))
            .perform(click())

        //check shows list is visible
        onView(withId(R.id.list)).check(matches(isDisplayed()))

        //verify content at position 0
        onView(withId(R.id.list))
            .check(matches(hasDescendant(withText("Test1"))))

        //verify content at position 1
        onView(withId(R.id.list))
            .check(matches(hasDescendant(withText("Test2"))))

    }

    @Test
    fun testInsertErrorWithEmptyText() {

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btnAddNew))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnShowAll))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnAddNew))
            .perform(click())

        onView(withId(R.id.inputTvShowTitle))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnSaveTVShow))
            .perform(click())

        onView(withText("Please Enter title of the Show")).check(matches(isDisplayed()))

    }


    @Test
    fun testInsertWithOnlyTitle() {

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btnAddNew))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnShowAll))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnAddNew))
            .perform(click())

        onView(withId(R.id.inputTvShowTitle))
            .check(matches(isDisplayed()))

        onView(withId(R.id.inputTvShowTitle))
            .perform(typeText("Test1"), closeSoftKeyboard())

        onView(withId(R.id.btnSaveTVShow))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnSaveTVShow))
            .perform(click())

    }


}