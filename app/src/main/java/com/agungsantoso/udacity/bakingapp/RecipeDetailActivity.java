package com.agungsantoso.udacity.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.agungsantoso.udacity.bakingapp.data.IngredientsParcel;
import com.agungsantoso.udacity.bakingapp.data.StepsParcel;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {


    public static boolean mTwoPane;
    public static String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Bundle data = getIntent().getExtras();
        recipeName = data.getString("recipe");
        getSupportActionBar().setTitle(recipeName);

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Bundle data = getIntent().getExtras();
        ArrayList<IngredientsParcel> ingrParcel = data.getParcelableArrayList("ingredients");
        String ingrText = "";
        for(int i = 0; i < ingrParcel.size(); i++) {
            ingrText += ingrParcel.get(i).getQuantity() + " " +
                        ingrParcel.get(i).getMeasure() + "   " +
                        ingrParcel.get(i).getIngredients() +
                        "\n";
        }

        TextView ingrTextView = (TextView) findViewById(R.id.ingredients);
        ingrTextView.setText(ingrText);

        ArrayList<StepsParcel> stpsParcel = data.getParcelableArrayList("steps");
        recyclerView.setAdapter(new RecipeDetailAdapter(stpsParcel));
    }
}
