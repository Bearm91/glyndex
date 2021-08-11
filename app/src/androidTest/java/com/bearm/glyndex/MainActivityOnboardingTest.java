package com.bearm.glyndex;


import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityOnboardingTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityOnboardingTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.gi_title_onboarding), withText(mActivityTestRule.getActivity().getString(R.string.info_onboarding_title)),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView.check(matches(withText(mActivityTestRule.getActivity().getString(R.string.info_onboarding_title))));

        ViewInteraction textView2 = onView(
                allOf(withText("Glyndex - Índice Glucémico"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        textView2.check(matches(withText("Glyndex - Índice Glucémico")));

        ViewInteraction button = onView(
                allOf(withId(R.id.btn_close), withText(mActivityTestRule.getActivity().getString(R.string.info_close_btn)),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.gi_source), withText("Fuente: Fundación para la Diabetes Novo Nordisk"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView3.check(matches(withText("Fuente: Fundación para la Diabetes Novo Nordisk")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.gi_source_gi), withText("Leer más sobre IG aquí y aquí."),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView4.check(matches(withText("Leer más sobre IG aquí y aquí.")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.gi_source_digital_link), withText("Ver tabla de alimentos en la web."),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView5.check(matches(withText("Ver tabla de alimentos en la web.")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.gi_source_pdf), withText("Descargar tabla de alimentos en PDF."),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView6.check(matches(withText("Descargar tabla de alimentos en PDF.")));
    }
}
