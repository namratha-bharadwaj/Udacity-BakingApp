package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class RecipeStep {

    @SerializedName("id")
    public int recipeStepID;

    @SerializedName("shortDescription")
    public String recipeStepShortDescription;

    @SerializedName("description")
    public String recipeStepFullDescription;

    @SerializedName("videoURL")
    public String recipeStepVideoURL;

    @SerializedName("thumbnailURL")
    public String recipeStepThumbURL;

    @ParcelConstructor
    public RecipeStep(int recipeStepID, String recipeStepShortDescription, String recipeStepFullDescription,
                      String recipeStepVideoURL, String recipeStepThumbURL) {
        this.recipeStepID = recipeStepID;
        this.recipeStepShortDescription = recipeStepShortDescription;
        this.recipeStepFullDescription = recipeStepFullDescription;
        this.recipeStepVideoURL = recipeStepVideoURL;
        this.recipeStepThumbURL = recipeStepThumbURL;
    }

    //Getters and setters
    public int getRecipeStepID() {
        return this.recipeStepID;
    }

    public void setRecipeStepID(int id) {
        this.recipeStepID = id;
    }

    public String getRecipeStepShortDescription() {
        return this.recipeStepShortDescription;
    }

    public void setRecipeStepShortDescription(String shortDesc) {
        this.recipeStepShortDescription = shortDesc;
    }

    public String getRecipeStepFullDescription() {
        return this.recipeStepFullDescription;
    }

    public void setRecipeStepFullDescription(String desc) {
        this.recipeStepFullDescription = desc;
    }

    public String getRecipeStepVideoURL() {
        return this.recipeStepVideoURL;
    }

    public void setRecipeStepVideoURL(String videoURL) {
        this.recipeStepVideoURL = videoURL;
    }

    public String getRecipeStepThumbURL() {
        return this.recipeStepThumbURL;
    }

    public void setRecipeStepThumbURL(String thumbURL) {
        this.recipeStepThumbURL = thumbURL;
    }

}
