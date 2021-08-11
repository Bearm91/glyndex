package com.bearm.glyndex;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityCategoryTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityCategoryTest() {
        ViewInteraction categoryTextView = onView(
                allOf(withId(R.id.tv_category_title),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        categoryTextView.check(matches(withText(mActivityTestRule.getActivity().getString(R.string.menu_category))));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.fab_search), withContentDescription(mActivityTestRule.getActivity().getString(R.string.search_button_content_description)),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.action_GI),
                        withParent(withParent(withId(R.id.toolbar))),
                        isDisplayed()));
        textView10.check(matches(isDisplayed()));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.action_about),
                        withParent(withParent(withId(R.id.toolbar))),
                        isDisplayed()));
        textView11.check(matches(isDisplayed()));
    }
}
