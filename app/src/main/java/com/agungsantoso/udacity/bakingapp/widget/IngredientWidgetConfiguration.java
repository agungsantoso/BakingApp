package com.agungsantoso.udacity.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.agungsantoso.udacity.bakingapp.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

public class IngredientWidgetConfiguration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_widget_configuration);

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        // https://stackoverflow.com/a/10566405/448050
        // Add radio button select
        RadioGroup group = (RadioGroup) findViewById(R.id.recipe_select);
        RadioButton button;

        String recipes = "";
        Prefs.getString("recipes", recipes);
        Log.d("Widget", "recipes = " + recipes);

        String[] rcpsArr = recipes.split(";");

        for(String rcp: rcpsArr) {
            button = new RadioButton(this);
            button.setText(rcp);
            group.addView(button);
        }

        // https://stackoverflow.com/a/16636877/448050
        // Add onclick listener
        final Button save = (Button) findViewById(R.id.config_save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Update widget
                Bundle extras = getIntent().getExtras();
                int widgetIds = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetIds);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }

}
