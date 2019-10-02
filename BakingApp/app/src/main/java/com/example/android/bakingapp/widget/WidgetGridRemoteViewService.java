package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeIngredient;

import java.util.List;

import static com.example.android.bakingapp.widget.RecipeWidgetProvider.ingredients;
import static com.example.android.bakingapp.widget.RecipeWidgetProvider.recipeName;

public class WidgetGridRemoteViewService extends RemoteViewsService {

    private List<RecipeIngredient> ingredientListForRemoteView;
    private String name;
    private int appwidgetId;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context = null;

        public GridRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            appwidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            name = intent.getStringExtra("name");
        }

        @Override
        public void onCreate() {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
            views.setTextViewText(R.id.tv_widget_recipe_name, name);

        }

        @Override
        public void onDataSetChanged() {
            ingredientListForRemoteView = ingredients;
            name = recipeName;

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientListForRemoteView != null ? ingredientListForRemoteView.size() : 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_item);
            views.setTextViewText(R.id.tv_widget_ingredient, ingredientListForRemoteView.get(i).getRecipeIngredientName());

            Intent populateIntent = new Intent();
            views.setOnClickFillInIntent(R.id.gv_parent_for_widget, populateIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
