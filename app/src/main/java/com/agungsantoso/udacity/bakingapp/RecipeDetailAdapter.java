package com.agungsantoso.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agungsantoso.udacity.bakingapp.data.StepsParcel;

import java.util.ArrayList;

/**
 * Created by agung.santoso on 9/11/2017.
 */

public class RecipeDetailAdapter
        extends RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder> {

    private final ArrayList<StepsParcel> mValues;

    public RecipeDetailAdapter(ArrayList<StepsParcel> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_detail_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getShortDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecipeDetailActivity.mTwoPane) {
                    Bundle arguments = new Bundle();
                    int id = position;
                    ArrayList<StepsParcel> steps = mValues;
                    arguments.putParcelableArrayList("steps", steps);
                    arguments.putString("video", steps.get(id).getVideoURL());
                    arguments.putString("ingredient", steps.get(id).getDescription());
                    arguments.putInt("size", steps.size());
                    RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                    fragment.setArguments(arguments);
                    Context context = v.getContext();
                    ((FragmentActivity)context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RecipeStepDetailActivity.class);
                    intent.putExtra("id", position);
                    intent.putParcelableArrayListExtra("steps", mValues);
                    context.startActivity(intent);
                }
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
        public StepsParcel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
