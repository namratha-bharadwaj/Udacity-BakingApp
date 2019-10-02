package com.example.android.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapp.model.RecipeIngredient;

import org.parceler.Parcels;

import java.util.List;

public class RecipeWidgetService extends IntentService {

    public static final String RECIPE_INGREDIENTS_LIST = "INGREDIENT_LIST_FROM_DETAIL_ACTIVITY";

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startWidgetService(Context context,
                                          List<RecipeIngredient> ingredientsListFromActivity,
                                          String name) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.putExtra(RECIPE_INGREDIENTS_LIST, Parcels.wrap(ingredientsListFromActivity));
        intent.putExtra("name", name);

        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            List<RecipeIngredient> ingredients = Parcels.unwrap(intent.getParcelableExtra(RECIPE_INGREDIENTS_LIST));
            handleWidgetUpdate(ingredients, intent.getStringExtra("name"));
        }

    }

    private void handleWidgetUpdate(List<RecipeIngredient> ingredientsListFromActivity, String name) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra(RECIPE_INGREDIENTS_LIST, Parcels.wrap(ingredientsListFromActivity));
        intent.putExtra("name", name);
        sendBroadcast(intent);
    }
}
