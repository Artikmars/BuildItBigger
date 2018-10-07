package com.udacity.gradle.builditbigger;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.paid.paid.debug.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.udacity.gradle.builditbigger.paid.paid.debug.MainActivity.resultFromTask;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        onView(withId(R.id.btn_tellJoke)).perform(click());
        //intended(hasExtra(JOKE_TAG, resultFromTask));
        onView(withId(R.id.tv_joke)).check(matches(not(withText(""))));
        onView(withId(R.id.tv_joke)).check(matches(withText(resultFromTask)));
    }
}
