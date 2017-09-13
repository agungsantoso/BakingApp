package com.agungsantoso.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agungsantoso.udacity.bakingapp.data.IngredientsParcel;
import com.agungsantoso.udacity.bakingapp.data.Recipe;
import com.agungsantoso.udacity.bakingapp.data.StepsParcel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
        holder.mContentView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(RecipeStepDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {*/
                Log.d("RecipeAdapter", "data = " + holder.mItem.getIngredients().toString());
                Context context = v.getContext();
                Intent intent = new Intent(context, RecipeDetailActivity.class);
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
                //}
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Recipe mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
