package com.example.carnot.retrofit;

import com.example.carnot.retrofit.models.GovDataModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GovDataApi {
    @GET("9ef84268-d588-465a-a308-a864a43d0070")
    Call<GovDataModel> getData(
            @Query("api-key") String key,
            @Query("format") String format,
            @Query("filters[state]") String state,
            @Query("offset") Integer offset,
            @Query("limit") Integer limit
    );
}
