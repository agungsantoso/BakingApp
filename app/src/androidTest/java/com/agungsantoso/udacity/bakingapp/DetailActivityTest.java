package com.agungsantoso.udacity.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.agungsantoso.udacity.bakingapp.TestUtils.withRecyclerView;

/**
 * Created by asanpra on 14/09/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    public static final String steps = "Melt butter and bittersweet chocolate.";

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule = new ActivityTestRule<>(RecipeActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecipeItem_OpensRecipeDetailActivity() {

        onView(withId(R.id.recipe_item_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withRecyclerView(R.id.item_list)
                .atPositionOnView(2, R.id.content))
                .check(matches(withText(steps)));

    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}

