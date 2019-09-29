package com.example.android.bakingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.RecipeDetailFragment;
import com.example.android.bakingapp.model.Recipe;

import org.parceler.Parcels;

public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe selectedRecipe;
    private String recipeName;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {
            Bundle arguments = getIntent().getExtras();
            selectedRecipe = Parcels.unwrap(arguments.getParcelable("selected_Recipe"));
            recipeName = selectedRecipe.getRecipeName();
            setTitle(recipeName);
        } else {
            recipeName = savedInstanceState.getString("Title");
            setTitle(recipeName);
        }

        setupFragment();
    }

    private void setupFragment() {
        RecipeDetailFragment recipeDetailFragObj = new RecipeDetailFragment().newInstance(selectedRecipe);
        FragmentManager fragManager = getSupportFragmentManager();
        fragManager.beginTransaction()
                .replace(R.id.recipe_detail_fragment, recipeDetailFragObj).addToBackStack(null)
                .commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title", recipeName);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
