package com.example.bigboss.bigboss;

import com.example.bigboss.bigboss.TabCategoryPOJO.TabBean;
import com.example.bigboss.bigboss.VideoGenralPOJO.GenralBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AllApiIneterface {


    @Multipart
    @POST("bigboss/?rest_route=/GetVideoByCategory/v1/video-by-category")
    Call<GenralBean> genra(
            @Part("catId") String catid);


    @GET("bigboss/?rest_route=/GetVideoCategory/v1/video-category")
    Call<TabBean> tabbean();



}
