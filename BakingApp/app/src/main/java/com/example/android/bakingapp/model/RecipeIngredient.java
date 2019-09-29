package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class RecipeIngredient {

    @SerializedName("quantity")
    public double recipeIngredientQuantity;

    @SerializedName("measure")
    public String recipeIngredientMeasurementMetric;

    @SerializedName("ingredient")
    public String recipeIngredientName;

    @ParcelConstructor
    public RecipeIngredient(double recipeIngredientQuantity,
                            String recipeIngredientMeasurementMetric, String recipeIngredientName) {
        this.recipeIngredientQuantity = recipeIngredientQuantity;
        this.recipeIngredientMeasurementMetric = recipeIngredientMeasurementMetric;
        this.recipeIngredientName = recipeIngredientName;
    }

    //Getters and setters
    public double getRecipeIngredientQuantity() {
        return this.recipeIngredientQuantity;
    }

    public void setRecipeIngredientQuantity(double quantity) {
        this.recipeIngredientQuantity = quantity;
    }

    public String getRecipeIngredientMeasurementMetric() {
        return this.recipeIngredientMeasurementMetric;
    }

    public void setRecipeIngredientMeasurementMetric(String recipeIngredientMeasurementMetric) {
        this.recipeIngredientMeasurementMetric = recipeIngredientMeasurementMetric;
    }

    public String getRecipeIngredientName() {
        return this.recipeIngredientName;
    }

    public void setRecipeIngredientName(String ingredientName) {
        this.recipeIngredientName = ingredientName;
    }

}