package com.example.android.bakingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeStepsRVAdapter;
import com.example.android.bakingapp.adapters.StepActivityPagerAdapter;
import com.example.android.bakingapp.fragments.StepDetailFragment;
import com.example.android.bakingapp.model.RecipeStep;
import com.google.android.material.tabs.TabLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepActivity extends AppCompatActivity implements RecipeStepsRVAdapter.RecipeStepItemClickListener  {

    private ViewPager recipeStepViewPager;
    private TabLayout recipeStepTabLayout;

    private List<RecipeStep> stepsList;
    private String recipeName;
    private int selectedStepIndex;
    private int currentItem;
    boolean isTablet;

    RecipeStepsRVAdapter mRecipeStepAdapter;

    private RecyclerView stepRV;

    private FragmentLifecycle fragmentLifecycleListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        isTablet = getResources().getBoolean(R.bool.isTablet);

        recipeStepViewPager = findViewById(R.id.recipe_step_activity_view_pager);
        recipeStepTabLayout = findViewById(R.id.recipe_step_activity_tab_layout);


        if (savedInstanceState == null) {
            setFragmentLifecycleListenerListener(fragmentLifecycleListener);
            init();

        }
    }

    public void setFragmentLifecycleListenerListener(FragmentLifecycle listener) {
        this.fragmentLifecycleListener = listener;
    }

    private void init() {
        setAndGetIntentValues();
        if(isTablet) {
            RecyclerView recyclerView = findViewById(R.id.rv_step_detail);
            setUpRecyclerView(recyclerView);
        }

        List<StepDetailFragment> stepDetailFragments = getStepDetailFragments();
        setUpPager(stepDetailFragments);
    }

    private void setAndGetIntentValues() {
        Intent intent = getIntent();
        stepsList = Parcels.unwrap(intent.getExtras().getParcelable("Selected_Steps"));
        recipeName = intent.getStringExtra("title");
        setTitle(recipeName);
        selectedStepIndex = intent.getIntExtra("Selected_Index", selectedStepIndex);

    }

    private void setUpRecyclerView(RecyclerView castedRV) {
        castedRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecipeStepAdapter = new RecipeStepsRVAdapter(stepsList, this, recipeName);
        castedRV.setAdapter(mRecipeStepAdapter);
    }

    @Override
    public void onStepItemClick(List<RecipeStep> recipeSteps, int itemPosition, String recipeName) {
        recipeStepViewPager.setCurrentItem(itemPosition, true);
    }

    private List<StepDetailFragment> getStepDetailFragments() {
        List<StepDetailFragment> fragments = new ArrayList<>();
        for (RecipeStep step : stepsList) {
            StepDetailFragment fragment = StepDetailFragment.newInstance(step);
            fragments.add(fragment);
        }
        return fragments;
    }

    private void setUpPager(List<StepDetailFragment> fragments) {
        StepActivityPagerAdapter pagerAdapter = new StepActivityPagerAdapter
                (getSupportFragmentManager(), fragments);
        recipeStepViewPager.setAdapter(pagerAdapter);
        recipeStepViewPager.setCurrentItem(selectedStepIndex);
        recipeStepTabLayout.setupWithViewPager(recipeStepViewPager, true);
        recipeStepViewPager.setOffscreenPageLimit(1);
        recipeStepViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        recipeStepViewPager.setCurrentItem(currentItem);
        init();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        currentItem = savedInstanceState.getInt("currentItem");
        recipeStepViewPager.setCurrentItem(currentItem);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentItem = recipeStepViewPager.getCurrentItem();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        currentItem = recipeStepViewPager.getCurrentItem();
        outState.putInt("Selected_Index", currentItem);
        super.onSaveInstanceState(outState);
    }


    public interface FragmentLifecycle {

        void onPauseFragment();

        void onResumeFragment();

    }
}
