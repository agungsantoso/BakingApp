package com.agungsantoso.udacity.bakingapp.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by agung.santoso on 9/11/2017.
 */

public interface ApiEndpointInterface {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
