package com.example.android.bakingapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.android.bakingapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityBasicTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>
            (MainActivity.class);

    @Test
    public void clickRecipeListItemFromMainActivity() throws InterruptedException {
        Thread.sleep(1000);

        onView(withId(R.id.activity_main_recipe_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_detail_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecipeListItemFromMainActivityAndValidateIngredientsTextView() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.activity_main_recipe_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.add_to_widget_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_ingredients_tv)).check(matches(isDisplayed()));
    }

    @Test
    public void clickStepRVItemAndOpenRecipeStepActivity() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.activity_main_recipe_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_detail_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_step_videos_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_step_activity_view_pager)).check(matches(isDisplayed()));
    }



}
