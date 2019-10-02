package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeDetailActivity;
import com.example.android.bakingapp.model.RecipeIngredient;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.widget.RecipeWidgetService.RECIPE_INGREDIENTS_LIST;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static List<RecipeIngredient> ingredients = new ArrayList<>();
    static String recipeName = null;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName) {

        Intent intent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        views.setPendingIntentTemplate(R.id.gv_parent_for_widget, pendingIntent);

        Intent intent1 = new Intent(context, WidgetGridRemoteViewService.class);
        intent1.putExtra("name", recipeName);
        //views.setOnClickPendingIntent(R.id.gv_parent_for_widget, pendingIntent);
        views.setRemoteAdapter(R.id.gv_parent_for_widget, intent1);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void manuallyUpdateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                                   int[] appWidgetIds, String passedInName) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, passedInName);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        for (int appWidgetId : appWidgetIds) {
//               updateAppWidget(context, appWidgetManager, appWidgetId, null);
//          }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.tv_widget_recipe_name, recipeName);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(new ComponentName(context, RecipeWidgetProvider.class));

        String action = intent.getAction();
        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            ingredients = Parcels.unwrap(intent.getParcelableExtra(RECIPE_INGREDIENTS_LIST));
            recipeName = intent.getStringExtra("name");
            manager.notifyAppWidgetViewDataChanged(ids, R.id.gv_parent_for_widget);
            RecipeWidgetProvider.manuallyUpdateRecipeWidgets(context, manager, ids, intent.getStringExtra("name"));
            super.onReceive(context, intent);
        }
    }
}

