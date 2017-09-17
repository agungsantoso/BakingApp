package com.agungsantoso.udacity.bakingapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ContentTest {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule = new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void contentTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_item_list),
                        withParent(withId(R.id.frameLayout)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.ingredients), withText("* 350.0 G   Bittersweet chocolate (60-70% cacao)\n* 226.0 G   unsalted butter\n* 300.0 G   granulated sugar\n* 100.0 G   light brown sugar\n* 5.0 UNIT   large eggs\n* 1.0 TBLSP   vanilla extract\n* 140.0 G   all purpose flour\n* 40.0 G   cocoa powder\n* 1.5 TSP   salt\n* 350.0 G   semisweet chocolate chips\n"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("* 350.0 G   Bittersweet chocolate (60-70% cacao) * 226.0 G   unsalted butter * 300.0 G   granulated sugar * 100.0 G   light brown sugar * 5.0 UNIT   large eggs * 1.0 TBLSP   vanilla extract * 140.0 G   all purpose flour * 40.0 G   cocoa powder * 1.5 TSP   salt * 350.0 G   semisweet chocolate chips ")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.content), withText("Melt butter and bittersweet chocolate."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Melt butter and bittersweet chocolate.")));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.item_list), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(2, click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.exo_overlay),
                        childAtPosition(
                                allOf(withId(R.id.player),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.item_detail), withText("2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between."),
                        childAtPosition(
                                allOf(withId(R.id.step_detail),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                2)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between.")));

        ViewInteraction button = onView(
                allOf(withId(R.id.previous),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                0),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.next),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        3),
                                1),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
