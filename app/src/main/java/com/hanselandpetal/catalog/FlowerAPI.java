package com.hanselandpetal.catalog;

import com.hanselandpetal.catalog.model.Flower;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by youshengchang on 4/11/15.
 */
public interface FlowerAPI {

    @GET("/feeds/flowers.json")
    public void getFeed(Callback<List<Flower>> response);
}
