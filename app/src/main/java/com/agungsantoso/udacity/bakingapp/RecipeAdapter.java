package com.agungsantoso.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.agungsantoso.udacity.bakingapp.data.IngredientsParcel;
import com.agungsantoso.udacity.bakingapp.data.Recipe;
import com.agungsantoso.udacity.bakingapp.data.StepsParcel;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;

/**
 * Created by agung.santoso on 9/11/2017.
 */

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final List<Recipe> mValues;

    public RecipeAdapter(List<Recipe> recipes) {
        mValues = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String recipeName = holder.mItem.getName();
        String image = holder.mItem.getImage();

        if(TextUtils.isEmpty(image)) {
            // Set image dynamically
            // https://stackoverflow.com/a/9481452/448050
            Context context = holder.itemView.getContext();
            String imageName = TextUtils.join("_", recipeName.split(" ")).toLowerCase();
            int imageId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            holder.mImageView.setImageResource(imageId);
        } else {
            // Get context in recyclerview adapter
            // https://stackoverflow.com/a/32137097/448050
            Glide.with(holder.mImageView.getContext())
                    .load(Uri.parse(image))
                    .into(holder.mImageView);
        }

        holder.mContentView.setText(recipeName);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("RecipeAdapter", "data = " + holder.mItem.getIngredients().toString());
                Context context = v.getContext();
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipe", holder.mItem.getName());
                List<Recipe.Ingredients> ingr = holder.mItem.getIngredients();
                ArrayList<IngredientsParcel> ingrParcel = new ArrayList<>();
                for(int i = 0; i < ingr.size(); i++) {
                    ingrParcel.add(new IngredientsParcel(
                            ingr.get(i).getQuantity(),
                            ingr.get(i).getMeasure(),
                            ingr.get(i).getIngredient()
                    ));
                }
                intent.putParcelableArrayListExtra("ingredients", ingrParcel);
                List<Recipe.Steps> stps = holder.mItem.getSteps();
                ArrayList<StepsParcel> stpsParcel = new ArrayList<>();
                for(int i = 0; i < stps.size(); i++) {
                    stpsParcel.add(new StepsParcel(
                            stps.get(i).getId(),
                            stps.get(i).getShortDescription(),
                            stps.get(i).getDescription(),
                            stps.get(i).getVideoURL(),
                            stps.get(i).getThumbnailURL()
                    ));
                }
                intent.putParcelableArrayListExtra("steps", stpsParcel);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mContentView;
        public Recipe mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.recipe_image);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
