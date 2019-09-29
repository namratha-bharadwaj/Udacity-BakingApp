package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelProperty;

import java.util.ArrayList;

@Parcel
public class Recipe {

    @SerializedName("id")
    public int recipeID;

    @SerializedName("name")
    public String recipeName;

    @SerializedName("steps")
    public ArrayList<RecipeStep> recipeSteps;

    @SerializedName("ingredients")
    public ArrayList<RecipeIngredient> recipeIngredients;

    @SerializedName("servings")
    public int recipeServingSize;

    @SerializedName("image")
    public String recipeImageURL;

    @ParcelConstructor
    public Recipe(int recipeID, String recipeName, ArrayList<RecipeStep> recipeSteps,
                  ArrayList<RecipeIngredient> recipeIngredients, int recipeServingSize) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeSteps = recipeSteps;
        this.recipeIngredients = recipeIngredients;
        this.recipeServingSize = recipeServingSize;
    }

    public Recipe() {}

    //Getters and setters

    public String getRecipeImageURL() {
        return this.recipeImageURL;
    }

    public void setRecipeImageURL(String imageURL) {
        this.recipeImageURL = imageURL;
    }

    public int getRecipeID() {
        return this.recipeID;
    }

    public void setRecipeID(int recipeID) { this.recipeID = recipeID; }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @ParcelProperty("recipe_steps")
    public ArrayList<RecipeStep> getRecipeSteps() {
        return this.recipeSteps;
    }

    public void setRecipeSteps(ArrayList<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    @ParcelProperty("ingredients")
    public ArrayList<RecipeIngredient> getRecipeIngredients() {
        return this.recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public int getRecipeServingSize() { return this.recipeServingSize; }

    public void setRecipeServingSize(int recipeServingSize) {
        this.recipeServingSize = recipeServingSize;
    }

}
