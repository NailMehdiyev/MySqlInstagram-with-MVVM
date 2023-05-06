package com.nailmehdiyev.mymysqlinstagram.api;

import com.nailmehdiyev.mymysqlinstagram.data.Responce.ApiResponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.Notificationlistresponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.PostListResponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.Postresponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.UserResponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.UserlistResponce;
import com.nailmehdiyev.mymysqlinstagram.data.model.Comment;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users.php")
    Call<UserlistResponce> usernamefilterbyname(@Query("username") String username);


    @GET("users.php")
    Call<UserResponce> alluserdetailsbyuserId(@Query("userid") int userid);


    @POST("users.php")
    @FormUrlEncoded
    Call<UserResponce> signin(@Field("username")String username,@Field("userpassword")String password);


    @POST("users.php")
    @FormUrlEncoded
    Call<UserResponce> signup(@Field("useremail")String useremail,@Field("username")String username,@Field("userpassword")String userpassword);


    @POST("users.php")
    @Multipart
    Call<UserResponce> changeProfilephoto(@Part("userid") int userid, @Part MultipartBody.Part image);


    @POST("users.php")
    @FormUrlEncoded
    Call<UserResponce> removeimage(@Field("userid") int userid, @Field("image_name") String imagename);


    @PUT("users.php")
    Call<UserResponce> updateuser(@Body User user);


    @POST("follow.php")
    @FormUrlEncoded
    Call<ApiResponce> followbutton(@Field("followingid") int followingid, @Field("followerid") int followerid);


    @DELETE("follow.php")
    Call<ApiResponce> unfollowbutton(@Query("followingid") int followingid, @Query("followerid") int followerid);


    /*****************************************************
     * Post Repository -> posts, post likes, saved posts *
     *****************************************************/


    @GET("posts.php")
    Call<PostListResponce> getPostsfilterbyfollowingid(@Query("userid") int userid, @Query("isfeed") int isfeed);


    @GET("posts.php")
    Call<PostListResponce> getPostsfilterbyuserid(@Query("userid") int userid, @Query("isfeed") int isfeed);


    @GET("posts.php")
    Call<Postresponce> getPostsdetailsfilterbypostid(@Query("postid") int postid);

    @POST("posts.php")
    @Multipart
    Call<ApiResponce> addPost(@Part MultipartBody.Part image,@Part("postowner") int postowner,@Part("postdescription") RequestBody postdescription );



    @PUT("posts.php")
    Call<ApiResponce> updatepost(@Body Post post);

    @DELETE("posts.php")
    Call<ApiResponce> deletepost(@Query("postid") int postid);


    @POST("post_likes.php")
    @FormUrlEncoded
    Call<ApiResponce> likepost(@Field("userid") int userid, @Field("postid") int postid);


    @DELETE("post_likes.php")

    Call<ApiResponce> unlikepost(@Query("userid") int userid, @Query("postid") int postid);


    @POST("saved_posts.php")
    @FormUrlEncoded
    Call<ApiResponce> savepost(@Field("userid") int userid, @Field("postid") int postid);


    @DELETE("saved_posts.php")
    Call<ApiResponce> unsavepost(@Query("userid") int userid, @Query("postid") int postid);


    @GET("saved_posts.php")
    Call<PostListResponce> getSavedPosts(@Query("userid") int userid );



    //coment

    @POST("comments.php")
    @FormUrlEncoded
    Call<ApiResponce> addcomment(@Field("commentowner") int commentowner, @Field("commenttext") String commenttext, @Field("commentpost") int commentpost);



    @DELETE("comments.php")
    Call<ApiResponce> deletecomment(@Query("commentid") int commentid);



    @PUT("comments.php")
    Call<ApiResponce> updateComment(
            @Body Comment comment
    );



   //Notification

    @GET("notifications.php")// our userid
    Call<Notificationlistresponce> getAllNotifications(@Query("userid") int userid );

    @PUT("notifications.php")
    Call<ApiResponce> markAllNotificationsAsSeen(@Body User user );//when the page opens. the user here is my user. let the owner of the notification come to me and update all notifications. it was seen as 1

    @DELETE("notifications.php")
    Call<ApiResponce> deleteAllNotifications(@Query("userid") int userid);//our userid





}
