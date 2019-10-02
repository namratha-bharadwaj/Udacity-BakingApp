package com.example.android.bakingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeItemAdapter;
import com.example.android.bakingapp.fragments.RecipeDetailFragment;
import com.example.android.bakingapp.httpClient.UnsafeOkHttpClient;
import com.example.android.bakingapp.model.JsonPlaceHolderAPI;
import com.example.android.bakingapp.model.Recipe;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeItemAdapter.OnRecipeItemClickListener {


    List<Recipe> recipeList;
    private static final String TAG = MainActivity.class.getName();

    RecyclerView recipeListRv;
    TextView emptyTV;
    private RecipeItemAdapter recipeAdapter;

    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyTV = findViewById(R.id.empty_text);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        setupRecyclerView();
    }

    public void setupRecyclerView() {
        RecyclerView.LayoutManager rvLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recipeListRv = findViewById(R.id.activity_main_recipe_list_rv);
        recipeListRv.setHasFixedSize(true);
        recipeListRv.setLayoutManager(rvLinearLayoutManager);
        getJsonResponseAndPopulateRecyclerView();
    }

    public void getJsonResponseAndPopulateRecyclerView() {
        JsonPlaceHolderAPI jsonPlaceHolderAPI = getJsonPlaceHolderAPI();
        Call<List<Recipe>> apiCall = jsonPlaceHolderAPI.getRecipeObjects();

        recipeList = new ArrayList<>();
        apiCall.enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse: getRecipeList call failed or the body is null: \n" + response.isSuccessful() + "\n" + response.toString());
                } else {
                    try {
                        recipeList = response.body();
                        recipeAdapter = new RecipeItemAdapter(recipeList, MainActivity.this::onRecipeItemClick);
                        recipeListRv.setAdapter(recipeAdapter);
                        recipeAdapter.notifyDataSetChanged();
                    } catch (NullPointerException e) {
                        Log.e(TAG, e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, t.getMessage());

            }
        });
    }


    public JsonPlaceHolderAPI getJsonPlaceHolderAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(JsonPlaceHolderAPI.class);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRecipeItemClick(Recipe recipeItemPosition) {
        setTitle(recipeItemPosition.getRecipeName());

        if (isTablet) {
            emptyTV.setVisibility(View.GONE);
            RecipeDetailFragment fragment = new RecipeDetailFragment().newInstance(recipeItemPosition);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_fragment_detail_container, fragment).addToBackStack("detailFragment")
                    .commit();
        } else {

            Bundle chosenRecipeItemBundle = new Bundle();
            chosenRecipeItemBundle.putParcelable("Recipe Chosen", Parcels.wrap(recipeItemPosition));
            final Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
            intent.putExtra("selected_Recipe", Parcels.wrap(recipeItemPosition));
            startActivity(intent);
        }

    }
}
