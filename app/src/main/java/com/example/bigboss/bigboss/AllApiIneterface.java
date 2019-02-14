package com.example.bigboss.bigboss;

import com.example.bigboss.bigboss.PlaySliderPOJO.PlayBean;
import com.example.bigboss.bigboss.SearchPojo.SearchBean;
import com.example.bigboss.bigboss.ShopTillPOJO.TillBean;
import com.example.bigboss.bigboss.ShoptabPOJO.ShopBean;
import com.example.bigboss.bigboss.TabCategoryPOJO.TabBean;
import com.example.bigboss.bigboss.TillCategory3POJO.ShopProductBean;
import com.example.bigboss.bigboss.TillSubCategory2.TillSubCatBean;
import com.example.bigboss.bigboss.VideoGenralPOJO.GenralBean;
import com.example.bigboss.bigboss.VideoUrlPOJO.VideourlBean;
import com.example.bigboss.bigboss.locationPOJO.locationBean;
import com.example.bigboss.bigboss.matchingPOJO.matchingBean;

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
            @Part("catId") String catid,
            @Part("locationId") String locationId
    );


    @GET("bigboss/?rest_route=/GetVideoCategory/v1/video-category")
    Call<TabBean> tabbean();


   /* @GET("bigboss/?rest_route=/GetShopCategory/v1/get-shop-category")
    Call<ShopBean> sho();
*/


    @Multipart
    @POST("bigboss/?rest_route=/GetShopCategory/v1/get-shop-category")
    Call<ShopBean> sho(
            @Part("locationId") String catssid);


    @GET("bigboss/?rest_route=/GetLocationCategory/v1/location-list")
    Call<locationBean> getLocations();


    @Multipart
    @POST("bigboss/?rest_route=/GetShopsubCategory1/v1/get-shop-sub-category1")
    Call<TillBean> till(
            @Part("catId") String catid ,
            @Part("locationId") String catssid);

    @Multipart
    @POST("bigboss/?rest_route=/GetShopsubCategory2/v1/get-shop-sub-category2")
    Call<TillSubCatBean> tillcat2(
            @Part("subCatId") String catid ,
            @Part("locationId") String ctid );

    @Multipart
    @POST("bigboss/?rest_route=/GetProductByid/v1/get-product")
    Call<PlayBean> play(
            @Part("productId") String catid,
            @Part("locationId") String locationId
    );


    @Multipart
    @POST("bigboss/?rest_route=/GetProductBysubcatid/v1/get-product-by-subcat")
    Call<ShopProductBean> shopproduct(
            @Part("subCatId") String subcatid,
            @Part("locationId") String locationId
    );


    @Multipart
    @POST("bigboss/?rest_route=/GetVideoByCategory/v1/video-by-category")
    Call<VideourlBean> video(
            @Part("catId") String cat);

    @Multipart
    @POST("bigboss/?rest_route=/GetMatchingProduct/v1/matching-product")
    Call<matchingBean> getMatchingData(
            @Part("matchingId") String cat);

    @Multipart
    @POST("bigboss/?rest_route=/SearchProduct/v1/get-product")
    Call<SearchBean> search(
            @Part("searchVal") String cat ,
            @Part("locationId") String c

    );

}
