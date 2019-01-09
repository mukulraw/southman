package com.example.bigboss.bigboss;

import com.example.bigboss.bigboss.PlaySliderPOJO.PlayBean;
import com.example.bigboss.bigboss.ShopTillPOJO.TillBean;
import com.example.bigboss.bigboss.ShoptabPOJO.ShopBean;
import com.example.bigboss.bigboss.TabCategoryPOJO.TabBean;
import com.example.bigboss.bigboss.TillSubCategory2.TillSubCatBean;
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


    @GET("bigboss/?rest_route=/GetShopCategory/v1/get-shop-category")
    Call<ShopBean> sho();

    @Multipart
    @POST("bigboss/?rest_route=/GetShopsubCategory1/v1/get-shop-sub-category1")
    Call<TillBean> till(
            @Part("catId") String catid);



    @Multipart
    @POST("bigboss/?rest_route=/GetShopsubCategory2/v1/get-shop-sub-category2")
    Call<TillSubCatBean> tillcat2(
            @Part("subCatId") String catid);


    @Multipart
    @POST("bigboss/?rest_route=/GetProductByid/v1/get-product")
    Call<PlayBean> play(
            @Part("productId") String catid);



}
