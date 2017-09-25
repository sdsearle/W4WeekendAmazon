package com.example.admin.w4weekendamazon;

import com.example.admin.w4weekendamazon.amazonmodel.AmazonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by admin on 9/18/2017.
 */

public class RetrofitHelper {
    private static final String BASE_URL = "http://de-coding-test.s3.amazonaws.com";

    public static Retrofit create(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;

    }

    //create a static method to ues the interface verbs
    public static Call<List<AmazonResponse>> createCall(){
        Retrofit retrofit = create();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService.getAmazonResponse();

    }

    //create an interface to have all the paths and verbs for the REST api to use
    interface ApiService{

        @GET("/books.json")
        Call<List<AmazonResponse>> getAmazonResponse();

    }

}
