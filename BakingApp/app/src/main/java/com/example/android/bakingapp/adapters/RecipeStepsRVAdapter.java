package com.example.android.bakingapp.adapters;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeStep;

import java.util.List;

public class RecipeStepsRVAdapter extends RecyclerView.Adapter<RecipeStepsRVAdapter.RecipeDetailViewHolder> {

    private List<RecipeStep> recipeStepsList;
    private String recipeName;

    private final RecipeStepItemClickListener recipeStepItemClickListener;

    public RecipeStepsRVAdapter(List<RecipeStep> recipeSteps, RecipeStepItemClickListener listener,
                                String name) {
        recipeStepsList = recipeSteps;
        recipeStepItemClickListener = listener;
        recipeName = name;
    }

    public interface RecipeStepItemClickListener {
        void onStepItemClick(List<RecipeStep> recipeSteps, int itemPosition, String recipeName);
    }

    @NonNull
    @Override
    public RecipeStepsRVAdapter.RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                          int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_step_item, parent, false);
        return new RecipeDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsRVAdapter.RecipeDetailViewHolder holder,
                                 int position) {
        RecipeStep stepItem = recipeStepsList.get(position);
        holder.stepItemNumberTv.setText(Integer.toString(stepItem.getRecipeStepID()));
        holder.recipeItemShortDescriptionTv.setText(String.format(stepItem.getRecipeStepShortDescription()));
        String recipeStepVideoThumbNailURL = stepItem.getRecipeStepThumbURL();

        if(null != recipeStepVideoThumbNailURL && recipeStepVideoThumbNailURL.endsWith(".mp4")) {
            Bitmap bMap = ThumbnailUtils.createVideoThumbnail(recipeStepVideoThumbNailURL, MediaStore.Video.Thumbnails.MINI_KIND);
            holder.videoThumbNailIv.setImageBitmap(bMap);
        } else {
            if (recipeStepVideoThumbNailURL.isEmpty() && !stepItem.getRecipeStepVideoURL().isEmpty()) {
                holder.videoThumbNailIv.setImageResource(R.drawable.image_placeholder);
            }
        }

    }

    @Override
    public int getItemCount() {
        return recipeStepsList != null ? recipeStepsList.size() : 0;
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder {

        TextView recipeItemShortDescriptionTv;
        TextView stepItemNumberTv;
        ImageView videoThumbNailIv;

        public RecipeDetailViewHolder(final View itemView) {
            super(itemView);
            recipeItemShortDescriptionTv = itemView.findViewById(R.id.recipe_step_item_short_description_tv);
            stepItemNumberTv = itemView.findViewById(R.id.recipe_step_item_number_tv);
            videoThumbNailIv = itemView.findViewById(R.id.recipe_step_item_video_image_iv);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (recipeStepItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        recipeStepItemClickListener.onStepItemClick(recipeStepsList, position, recipeName);
                    }

                }
            });

        }
    }

}
