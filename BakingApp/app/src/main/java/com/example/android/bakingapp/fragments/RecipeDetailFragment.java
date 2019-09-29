package com.example.android.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeStepActivity;
import com.example.android.bakingapp.adapters.RecipeStepsRVAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeIngredient;
import com.example.android.bakingapp.model.RecipeStep;

import org.parceler.Parcels;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment implements RecipeStepsRVAdapter.RecipeStepItemClickListener {

    TextView ingredientsListTv;
    RecyclerView recipeStepsRv;
    private List<RecipeStep> recipeStepsList;
    private Recipe selectedRecipe;
    private List<RecipeIngredient> recipeIngredientsList;

    public RecipeDetailFragment() {

    }

    public static RecipeDetailFragment newInstance(Recipe selectedRecipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable("selected_Recipe", Parcels.wrap(selectedRecipe));
        fragment.setArguments(arguments);
        return fragment;

    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container,
                false);
        ingredientsListTv = rootView.findViewById(R.id.recipe_ingredients_tv);
        recipeStepsRv = rootView.findViewById(R.id.recipe_step_videos_rv);

        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                selectedRecipe = Parcels.unwrap(getArguments().getParcelable("selected_Recipe"));
                recipeIngredientsList = selectedRecipe.getRecipeIngredients();
                recipeStepsList = selectedRecipe.getRecipeSteps();
                setupIngredientsTv();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupRecipeStepsRecyclerView();

        return rootView;

    }

    private void setupIngredientsTv() {
        for (int i = 0; i < recipeIngredientsList.size(); i++) {
            ingredientsListTv.append("\u2022 " + recipeIngredientsList.get(i).getRecipeIngredientName()
                    + ": " + recipeIngredientsList.get(i)
                    .getRecipeIngredientQuantity() + " " + recipeIngredientsList.get(i)
                    .getRecipeIngredientMeasurementMetric() + "\n\n");

        }

    }

    private void setupRecipeStepsRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recipeStepsRv.setLayoutManager(mLayoutManager);
        RecipeStepsRVAdapter recipeDetailAdapter = new RecipeStepsRVAdapter(recipeStepsList, this,
                selectedRecipe.getRecipeName());
        recipeStepsRv.setAdapter(recipeDetailAdapter);
    }

    @Override
    public void onStepItemClick(List<RecipeStep> recipeSteps, int itemPosition, String recipeName) {
        Bundle stepBundle = new Bundle();
        stepBundle.putInt("Selected_Index", itemPosition);
        final Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
        intent.putExtra("title", recipeName);
        intent.putExtra("Selected_Steps", Parcels.wrap(recipeSteps));
        intent.putExtra("Selected_Index", itemPosition);
        startActivity(intent);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            selectedRecipe = Parcels.unwrap(savedInstanceState.getParcelable("selected_Recipe"));
            assert selectedRecipe != null;
            recipeIngredientsList = selectedRecipe.getRecipeIngredients();
            recipeStepsList = selectedRecipe.getRecipeSteps();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectedRecipe = Parcels.unwrap(getArguments().getParcelable("selected_Recipe"));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("selected_Recipe", Parcels.wrap(selectedRecipe));
        outState.putString("Title", selectedRecipe.getRecipeName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
