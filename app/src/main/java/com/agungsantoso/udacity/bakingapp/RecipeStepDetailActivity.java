package com.agungsantoso.udacity.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.agungsantoso.udacity.bakingapp.data.StepsParcel;

import java.util.ArrayList;

public class RecipeStepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        getSupportActionBar().setTitle(RecipeDetailActivity.recipeName);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            int id = getIntent().getIntExtra("id", 0);
            arguments.putInt("id", id);
            ArrayList<StepsParcel> steps = getIntent().getParcelableArrayListExtra("steps");
            arguments.putParcelableArrayList("steps", steps);
            arguments.putString("video", steps.get(id).getVideoURL());
            arguments.putString("thumbnail", steps.get(id).getThumbnailURL());
            arguments.putString("ingredient", steps.get(id).getDescription());
            arguments.putInt("size", steps.size());
            RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NavUtils.navigateUpTo(this, intent);
        return true;
    }
}
