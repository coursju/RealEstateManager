package com.openclassrooms.realestatemanager.ui;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controler.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RealEstateUiTest {

    private Integer timeToSleep = 1000;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.INTERNET",
                    "android.permission.ACCESS_WIFI_STATE",
                    "android.permission.CAMERA",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void detailsIsShowTest() throws InterruptedException {
        Thread.sleep(timeToSleep);
        onView(allOf(withId(R.id.list), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.details_type_text))
                .check(matches(isDisplayed()));
    }

    @Test
    public void modifyIsShowTest() throws InterruptedException {
        Thread.sleep(timeToSleep);
        onView(allOf(withId(R.id.list), isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));
        onView(allOf(withId(R.id.estateDetailsFloatingBtn), isDisplayed()))
                .perform(click());
        onView(allOf(withText(mActivityTestRule.getActivity().getResources().getString(R.string.modify_submit_button))))
                .check(matches(withText(mActivityTestRule.getActivity().getResources().getString(R.string.modify_submit_button))));
    }

    @Test
    public void createIsShowTest() throws InterruptedException {
        Thread.sleep(timeToSleep);
        onView(withId(R.id.estatesListFloatingBtn))
                .perform(click());
        onView(allOf(withText(mActivityTestRule.getActivity().getResources().getString(R.string.create_submit_button))))
                .check(matches(withText(mActivityTestRule.getActivity().getResources().getString(R.string.create_submit_button))));
    }

    @Test
    public void isSearchDialogShowTest() throws InterruptedException {
        Thread.sleep(timeToSleep);
        onView(withId(R.id.menu_search))
                .perform(click());
        onView(withId(R.id.search_type_text_title))
                .check(matches(isDisplayed()));
    }

    @Test
    public void isMapShowTest() throws InterruptedException {
        Thread.sleep(timeToSleep);
        openContextualActionModeOverflowMenu();
        onView(withText(mActivityTestRule.getActivity().getResources().getString(R.string.menu_map)))
                .perform(click());
        onView(withId(R.id.maps_map))
                .check(matches(isDisplayed()));
    }

    @Test
    public void isLoanShowTest() throws InterruptedException {
        Thread.sleep(timeToSleep);
        openContextualActionModeOverflowMenu();
        onView(withText(mActivityTestRule.getActivity().getResources().getString(R.string.menu_loan)))
                .perform(click());
        onView(withId(R.id.loan_result_txt_zone))
                .check(matches(isDisplayed()));
    }
}
