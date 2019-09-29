package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.R;

import java.util.List;

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.RecipeItemViewHolder> {

    private List<Recipe> recipeList;
    private final OnRecipeItemClickListener clickListener;

    public RecipeItemAdapter(List<Recipe> recipeList, OnRecipeItemClickListener listener) {
        this.recipeList = recipeList;
        this.clickListener = listener;

    }

    public interface OnRecipeItemClickListener {
        void onRecipeItemClick(Recipe recipeItemPosition);

    }

//    public void setRecipes(List<Recipe> recipeList) {
//        this.recipeList = recipeList;
//        //FIXME: do not call this method
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public RecipeItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.main_activity_recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttach = false;
        View view = inflater.inflate(layoutId, viewGroup, shouldAttach);
        return new RecipeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeItemViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        String recipeIDInString = String.valueOf(recipe.getRecipeID());
        holder.recipeItemId.setText(recipeIDInString);
        holder.recipeItemName.setText(String.format(recipe.getRecipeName()));
        holder.recipeItemServingSize.setText(String.format("Serves "+recipe.getRecipeServingSize()));
        holder.recipeItemStepCount.setText(String.format("Steps "+recipe.getRecipeSteps().size()));

    }

    @Override
    public int getItemCount() {
        return (null != recipeList ? recipeList.size():0);
    }

    public class RecipeItemViewHolder extends RecyclerView.ViewHolder {

        int recipeItemPosition = -1;
        TextView recipeItemId;
        TextView recipeItemName;
        TextView recipeItemServingSize;
        TextView recipeItemStepCount;

        public RecipeItemViewHolder(final View itemView) {
            super(itemView);
            recipeItemId = itemView.findViewById(R.id.recipe_item_card_id_tv);
            recipeItemName = itemView.findViewById(R.id.recipe_item_name_tv);
            recipeItemServingSize = itemView.findViewById(R.id.recipe_item_serving_size_tv);
            recipeItemStepCount = itemView.findViewById(R.id.recipe_item_steps_count_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (clickListener != null && position != RecyclerView.NO_POSITION) {
                        clickListener.onRecipeItemClick(recipeList.get(position));
                    }

                }
            });

        }
    }

}
