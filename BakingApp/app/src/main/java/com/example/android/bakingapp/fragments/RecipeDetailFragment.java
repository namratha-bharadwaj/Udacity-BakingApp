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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeStepActivity;
import com.example.android.bakingapp.adapters.RecipeStepsRVAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeIngredient;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.widget.RecipeWidgetService;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment implements RecipeStepsRVAdapter.RecipeStepItemClickListener {

    public static final String SELECTED_RECIPE_KEY = "selected_Recipe";
    public static final String SELECTED_INDEX_KEY = "Selected_Index";
    public static final String SELECTED_STEPS_KEY = "Selected_Steps";
    public static final String TITLE_KEY = "title";

    private TextView ingredientsListTv;
    private RecyclerView recipeStepsRv;
    private Button addToWidgetButton;
    private List<RecipeStep> recipeStepsList;
    private Recipe selectedRecipe;
    private List<RecipeIngredient> recipeIngredientsList;

    public RecipeDetailFragment() {

    }

    public static RecipeDetailFragment newInstance(Recipe selectedRecipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(SELECTED_RECIPE_KEY, Parcels.wrap(selectedRecipe));
        fragment.setArguments(arguments);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container,
                false);
        ingredientsListTv = rootView.findViewById(R.id.recipe_ingredients_tv);
        recipeStepsRv = rootView.findViewById(R.id.recipe_step_videos_rv);
        addToWidgetButton = rootView.findViewById(R.id.add_to_widget_btn);

        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                selectedRecipe = Parcels.unwrap(getArguments().getParcelable(SELECTED_RECIPE_KEY));
                recipeIngredientsList = selectedRecipe.getRecipeIngredients();
                recipeStepsList = selectedRecipe.getRecipeSteps();
                List<RecipeIngredient> recipeIngredientListToAddToWidget = setupIngredientsTvAndGetList();
                addToWidgetButton.setOnClickListener(view -> addToWidget(recipeIngredientListToAddToWidget));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupRecipeStepsRecyclerView();

        return rootView;

    }

    private void addToWidget(List<RecipeIngredient> ingredientsToPassToWidget) {
        RecipeWidgetService.startWidgetService(getContext(), ingredientsToPassToWidget,
                selectedRecipe.getRecipeName());
        Toast.makeText(getActivity(), "Widget Updated!", Toast.LENGTH_LONG).show();
    }

    private List<RecipeIngredient> setupIngredientsTvAndGetList() {
        List<RecipeIngredient> recipeIngredientsListToAddToWidget = new ArrayList<>();
        for (int i = 0; i < recipeIngredientsList.size(); i++) {
            ingredientsListTv.append("\u2022 " + recipeIngredientsList.get(i).getRecipeIngredientName()
                    + ": " + recipeIngredientsList.get(i)
                    .getRecipeIngredientQuantity() + " " + recipeIngredientsList.get(i)
                    .getRecipeIngredientMeasurementMetric() + "\n\n");
            recipeIngredientsListToAddToWidget.add(recipeIngredientsList.get(i));

        }
        return recipeIngredientsListToAddToWidget;

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
        stepBundle.putInt(SELECTED_INDEX_KEY, itemPosition);
        final Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
        intent.putExtra(TITLE_KEY, recipeName);
        intent.putExtra(SELECTED_STEPS_KEY, Parcels.wrap(recipeSteps));
        intent.putExtra(SELECTED_INDEX_KEY, itemPosition);
        startActivity(intent);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            selectedRecipe = Parcels.unwrap(savedInstanceState.getParcelable(SELECTED_RECIPE_KEY));
            assert selectedRecipe != null;
            recipeIngredientsList = selectedRecipe.getRecipeIngredients();
            recipeStepsList = selectedRecipe.getRecipeSteps();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectedRecipe = Parcels.unwrap(getArguments().getParcelable(SELECTED_RECIPE_KEY));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_RECIPE_KEY, Parcels.wrap(selectedRecipe));
        outState.putString("Title", selectedRecipe.getRecipeName());
    }

}
