package com.agungsantoso.udacity.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.agungsantoso.udacity.bakingapp.R;

/**
 * Created by asanpra on 14/09/2017.
 */

public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipe, String ingredient) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);

        Log.d("widgetprov", "ingr = " + ingredient);

        String rcpCnt = "<b>" + recipe + "</b><br/>" + ingredient;
        // Make specific text bold
        // https://stackoverflow.com/a/14371107/448050
        views.setTextViewText(R.id.widget_content, Html.fromHtml(rcpCnt));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            SharedPreferences sharedPref = context.getSharedPreferences("BakingApp", Context.MODE_PRIVATE);
            String recipe = sharedPref.getString("recipe", "");
            String ingredient = sharedPref.getString(recipe, "");
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe, ingredient);
        }
    }

}