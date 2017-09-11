package com.agungsantoso.udacity.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.agungsantoso.udacity.bakingapp.data.ApiEndpointInterface;
import com.agungsantoso.udacity.bakingapp.data.Recipe;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private boolean mTwoPane;
    private RecipeAdapter mAdapter;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Accept", "Application/JSON").build();
                                return chain.proceed(request);
                            }
                        }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface service = retrofit.create(ApiEndpointInterface.class);

        Call<List<Recipe>> call = service.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                Log.d("MainActivity", "Status Code = " + response.code());

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    List<Recipe> result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));

                    // This is where data loads
                    mAdapter = new RecipeAdapter(result);


                    //attach to recyclerview
                    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                    rv = (RecyclerView) findViewById(R.id.recipe_item_list);
                    rv.setLayoutManager(llm);
                    rv.setAdapter(mAdapter);

                    if (findViewById(R.id.item_detail_container) != null) {
                        // The detail container view will be present only in the
                        // large-screen layouts (res/values-w900dp).
                        // If this view is present, then the
                        // activity should be in two-pane mode.
                        mTwoPane = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Main Activity", "failure call = " + t.toString());
            }
        });
    }
}
