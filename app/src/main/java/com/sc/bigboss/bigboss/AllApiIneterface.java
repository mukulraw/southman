package com.sc.bigboss.bigboss;

import com.sc.bigboss.bigboss.PlaySliderPOJO.PlayBean;
import com.sc.bigboss.bigboss.SearchPojo.SearchBean;
import com.sc.bigboss.bigboss.ShopTillPOJO.TillBean;
import com.sc.bigboss.bigboss.ShoptabPOJO.ShopBean;
import com.sc.bigboss.bigboss.TabCategoryPOJO.TabBean;
import com.sc.bigboss.bigboss.TillCategory3POJO.ShopProductBean;
import com.sc.bigboss.bigboss.TillSubCategory2.TillSubCatBean;
import com.sc.bigboss.bigboss.VideoGenralPOJO.GenralBean;
import com.sc.bigboss.bigboss.VideoUrlPOJO.VideourlBean;
import com.sc.bigboss.bigboss.addCartRequestPOJO.addCartRequestBean;
import com.sc.bigboss.bigboss.bannerPOJO.bannerBean;
import com.sc.bigboss.bigboss.benefits3POJO.benefits3Bean;
import com.sc.bigboss.bigboss.cartPOJO.cartBean;
import com.sc.bigboss.bigboss.createOrderPOJO.createOrderBean;
import com.sc.bigboss.bigboss.gPayPOJO.gPayBean;
import com.sc.bigboss.bigboss.getPerksPOJO.getPerksBean;
import com.sc.bigboss.bigboss.getPlayPOJO.getPlayBean;
import com.sc.bigboss.bigboss.locationPOJO.locationBean;
import com.sc.bigboss.bigboss.matchByIdPOJO.matchByIdBean;
import com.sc.bigboss.bigboss.matchPOJO.matchBean;
import com.sc.bigboss.bigboss.matchingPOJO.matchingBean;
import com.sc.bigboss.bigboss.onlinePayPOJO.onlinePayBean;
import com.sc.bigboss.bigboss.pendingOrderPOJO.pendingOrderBean;
import com.sc.bigboss.bigboss.playDataPOJO.playDataBean;
import com.sc.bigboss.bigboss.prodList2POJO.prodList2Bean;
import com.sc.bigboss.bigboss.qrPOJO.qrBean;
import com.sc.bigboss.bigboss.registerPlayPOJO.registerPlayBean;
import com.sc.bigboss.bigboss.scratchCardPOJO.scratchCardBean;
import com.sc.bigboss.bigboss.subCat3POJO.subCat3Bean;
import com.sc.bigboss.bigboss.usersPOJO.usersBean;
import com.sc.bigboss.bigboss.vHistoryPOJO.vHistoryBean;
import com.sc.bigboss.bigboss.voucherHistoryPOJO.voucherHistoryBean;
import com.sc.bigboss.bigboss.vouchersPOJO.vouchersBean;
import com.sc.bigboss.bigboss.winnersPOJO.winnersBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface AllApiIneterface {


    @Multipart
    @POST("southman/?rest_route=/GetVideoByCategory/v1/video-by-category")
    Call<GenralBean> genra(
            @Part("catId") String catid,
            @Part("locationId") String locationId
    );


    @GET("southman/?rest_route=/GetVideoCategory/v1/video-category")
    Call<TabBean> tabbean();


   /* @GET("southman/?rest_route=/GetShopCategory/v1/get-shop-category")
    Call<ShopBean> sho();
*/

    @Multipart
    @POST("southman/?rest_route=/GetShopCategory/v1/get-shop-category")
    Call<ShopBean> sho(
            @Part("locationId") String catssid);


    @GET("southman/?rest_route=/GetLocationCategory/v1/location-list")
    Call<locationBean> getLocations();


    @Multipart
    @POST("southman/?rest_route=/GetShopsubCategory1/v1/get-shop-sub-category1")
    Call<TillBean> till(
            @Part("catId") String catid ,
            @Part("locationId") String catssid);


    @Multipart
    @POST("southman/?rest_route=/GetShopsubCategory2/v1/get-shop-sub-category2")
    Call<TillSubCatBean> tillcat2(
            @Part("subCatId") String catid ,
            @Part("locationId") String ctid );

    @Multipart
    @POST("southman/api/getProd3.php")
    Call<vouchersBean> getProd3(
            @Part("sub_category2") String catid,
            @Part("locationId") String location,
            @Part("percentage") String percentage,
            @Part("client") String client
    );

    @Multipart
    @POST("southman/api/getSubCat3.php")
    Call<subCat3Bean> subCat3(
            @Part("subcat1_id") String catid,
            @Part("locationId") String location,
            @Part("client") String client
    );

    @Headers({"Content-Type: application/json"})
    @POST("southman/api/addCart.php")
    Call<vouchersBean> addCart(@Body addCartRequestBean body);

    @Multipart
    @POST("southman/api/clearCart.php")
    Call<vouchersBean> clearCart(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("southman/api/deleteCart.php")
    Call<vouchersBean> deleteCart(
            @Part("id") String id
    );

    @Multipart
    @POST("southman/api/updateCart.php")
    Call<vouchersBean> updateCart(
            @Part("id") String id,
            @Part("quantity") String quantity,
            @Part("unit_price") String unit_price
    );

    @Multipart
    @POST("southman/api/getCart.php")
    Call<cartBean> getCart(
            @Part("user_id") String user_id,
            @Part("client") String client
    );

    @Multipart
    @POST("southman/api/buyVouchers.php")
    Call<gPayBean> buyVouchers(
            @Part("user_id") String user_id,
            @Part("client") String client,
            @Part("amount") String amount,
            @Part("txn") String txn
    );

    @Multipart
    @POST("southman/api/createOrder.php")
    Call<createOrderBean> createOrder(
            @Part("user_id") String user_id,
            @Part("client") String client,
            @Part("amount") String amount,
            @Part("txn") String txn
    );

    @Multipart
    @POST("southman/api/requestOrder.php")
    Call<createOrderBean> requestOrder(
            @Part("user_id") String user_id,
            @Part("client") String client,
            @Part("txn") String txn
    );

    @Multipart
    @POST("southman/api/getSingleOrder.php")
    Call<gPayBean> getSingleOrder(
            @Part("id") String id
    );

    @Multipart
    @POST("southman/api/getProd2.php")
    Call<prodList2Bean> getProd2(
            @Part("sub_category2") String catid,
            @Part("locationId") String location
    );

    @Multipart
    @POST("southman/?rest_route=/GetProductByid/v1/get-product")
    Call<PlayBean> play(
            @Part("productId") String catid,
            @Part("locationId") String locationId
    );


    @Multipart
    @POST("southman/?rest_route=/GetProductBysubcatid/v1/get-product-by-subcat")
    Call<ShopProductBean> shopproduct(
            @Part("subCatId") String subcatid,
            @Part("locationId") String locationId,
            @Part("type") String type
    );


    @Multipart
    @POST("southman/?rest_route=/GetVideoByCategory/v1/video-by-category")
    Call<VideourlBean> video(
            @Part("catId") String cat);


    @Multipart
    @POST("southman/?rest_route=/GetMatchingProduct/v1/matching-product")
    Call<matchingBean> getMatchingData(
            @Part("matchingId") String cat);


    @Multipart
    @POST("southman/api/search.php")
    Call<SearchBean> search(
            @Part("query") String query,
            @Part("locationId") String c

    );

    @Multipart
    @POST("southman/api/registerPlay.php")
    Call<registerPlayBean> registerPlay(
            @Part("playId") String playId,
            @Part("name") String name,
            @Part("phone") String phone,
            @Part("deviceId") String deviceId,
            @Part("ipaddress") String ipaddress,
            @Part("token") String token,
            @Part("sku") String sku,
            @Part MultipartBody.Part file1
    );


    @Multipart
    @POST("southman/api/uploadBill.php")
    Call<scratchCardBean> uploadBill(
            @Part("clientId") String client,
            @Part("userId") String userid,
            @Part MultipartBody.Part file1
    );


    @Multipart
    @POST("southman/api/playBid.php")
    Call<registerPlayBean> playBid(
            @Part("playId") String playId,
            @Part("userId") String userId,
            @Part("bid") String bid
    );

    @Multipart
    @POST("southman/api/getPlayData.php")
    Call<playDataBean> getPlayData(
            @Part("playId") String playId
    );

    @Multipart
    @POST("southman/api/getPlay.php")
    Call<getPlayBean> getPlay(
            @Part("locationId") String c
    );

    @Multipart
    @POST("southman/api/endPlay.php")
    Call<registerPlayBean> endPlay(
            @Part("playId") String playId,
            @Part("wid") String wid
    );


    @GET("southman/api/getWinners.php")
    Call<winnersBean> getWiners();

    @GET("southman/api/getBanner.php")
    Call<bannerBean> getBanners();

    @Multipart
    @POST("southman/api/getPerks.php")
    Call<getPerksBean> getPerks(
            @Part("deviceId") String phone
    );

    @Multipart
    @POST("southman/api/getPerks.php")
    Call<getPerksBean> getPerks2(
            @Part("deviceId") String phone,
            @Part("client") String client
    );

    @Multipart
    @POST("southman/api/getCash.php")
    Call<scratchCardBean> getScratchCards(
            @Part("id") String id,
            @Part("client") String client
    );

    @Multipart
    @POST("southman/api/getRedeem.php")
    Call<scratchCardBean> getRedeemed(
            @Part("id") String id,
            @Part("date") String date
    );

    @Multipart
    @POST("southman/api/getRedeem21.php")
    Call<vHistoryBean> getRedeemed21(
            @Part("id") String id,
            @Part("date") String date
    );

    @Multipart
    @POST("southman/api/getOrderHistory.php")
    Call<voucherHistoryBean> getOrderHistory(
            @Part("order_id") String order_id
    );

    @Multipart
    @POST("southman/api/getRedeem2.php")
    Call<vHistoryBean> getRedeemed2(
            @Part("id") String id,
            @Part("date") String date
    );

    @Multipart
    @POST("southman/api/generateBill.php")
    Call<scratchCardBean> generateBill(
            @Part("id") String id
    );


    @Multipart
    @POST("southman/api/getRedeem3.php")
    Call<scratchCardBean> getRedeemed3(
            @Part("id") String id,
            @Part("date") String date
    );


    @Multipart
    @POST("southman/api/updateScratch.php")
    Call<voucherHistoryBean> updateScratch(
            @Part("scratch") String scratch
    );



    @Multipart
    @POST("southman/api/buyPerks.php")
    Call<scratchCardBean> buyPerks(
            @Part("deviceId") String id,
            @Part("client") String client,
            @Part("value") String value,
            @Part("type") String type,
            @Part("code") String code,
            @Part("price") String price
    );

    @Multipart
    @POST("southman/api/buyCash.php")
    Call<scratchCardBean> buyCash(
            @Part("deviceId") String id,
            @Part("client") String client,
            @Part("value") String value,
            @Part("type") String type,
            @Part("table") String table,
            @Part("cash") String cash,
            @Part("scratch") String scratch,
            @Part("take") String take,
            @Part MultipartBody.Part file1
    );


    @Multipart
    @POST("southman/api/redeem.php")
    Call<scratchCardBean> redeem(
            @Part("id") String id,
            @Part("deviceId") String device,
            @Part("value") String value,
            @Part("client") String client,
            @Part("type") String type,
            @Part("table") String table,
            @Part("cash") String cash,
            @Part("take") String take,
            @Part MultipartBody.Part file1
    );


    @Multipart
    @POST("southman/api/register.php")
    Call<scratchCardBean> register(
            @Part("deviceId") String id,
            @Part("name") String name,
            @Part("token") String token,
            @Part("phone") String phone
    );


    // cash
    @Multipart
    @POST("southman/api/updateOrder.php")
    Call<scratchCardBean> updateOrder(
            @Part("id") String id,
            @Part("cash") String cash,
            @Part("scratch") String scratch
    );

    @Multipart
    @POST("southman/api/onlinePay.php")
    Call<onlinePayBean> onlinePay(
            @Part("id") String id,
            @Part("pid") String pid,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("southman/api/freePay.php")
    Call<onlinePayBean> freePay(
            @Part("id") String id,
            @Part("pid") String pid,
            @Part("user_id") String user_id
    );


    @Multipart
    @POST("southman/api/cashPay.php")
    Call<onlinePayBean> cashPay(
            @Part("id") String id,
            @Part("pid") String pid,
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("southman/api/getSingleOrder2.php")
    Call<onlinePayBean> getSingleOrder2(
            @Part("id") String id
    );

    @Multipart
    @POST("southman/api/cancelOrder.php")
    Call<scratchCardBean> cancelOrder(
            @Part("id") String id
    );

    @Multipart
    @POST("southman/api/getOrderHistory2.php")
    Call<benefits3Bean> getOrderHistory2(
            @Part("order_id") String order_id
    );

    @Multipart
    @POST("southman/api/getQR.php")
    Call<qrBean> getQR(
            @Part("client") String client
    );

    // scratch
    @Multipart
    @POST("southman/api/updateOrder2.php")
    Call<scratchCardBean> updateOrder2(
            @Part("id") String id,
            @Part("cash") String cash,
            @Part("scratch") String scratch,
            @Part("sid") String sid
    );

    @Multipart
    @POST("southman/api/getPending.php")
    Call<pendingOrderBean> getPending(
            @Part("id") String id,
            @Part("client") String client,
            @Part("table") String table
    );

    @Multipart
    @POST("southman/api/request.php")
    Call<scratchCardBean> request(
            @Part("deviceId") String id,
            @Part("text") String name,
            @Part("phone") String phone
    );

    @Multipart
    @POST("southman/api/getMatchingProd.php")
    Call<matchBean> getMatch(
            @Part("type") String type,
            @Part("wear") String wear,
            @Part("locationId") String locationId
    );

    @Multipart
    @POST("southman/api/getMatchingProdByCat.php")
    Call<matchByIdBean> getMatchById(
            @Part("id") String id
    );
    
    @Multipart
    @POST("southman/api/getNotification.php")
    Call<List<notiBean>> getNoti(
            @Part("id") String id
    );

    @GET("southman/api/getUsers.php")
    Call<usersBean> getUsers();

    @Multipart
    @POST("southman/api/transfer_amount.php")
    Call<usersBean> transfer(
            @Part("id") String id,
            @Part("tid") String tid,
            @Part("amount") String amount
    );

    @Multipart
    @POST("southman/api/transfer_scratch.php")
    Call<usersBean> transfer2(
            @Part("id") String id,
            @Part("tid") String tid,
            @Part("amount") String amount
    );

    @Multipart
    @POST("southman/api/getTables.php")
    Call<tablebean> getTables(
            @Part("client") String client
    );

}
