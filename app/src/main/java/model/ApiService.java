package model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/register")
    Call<RegisterResponse> register(
            @Body RegisterRequest request
    );

    @POST("/api/login")
    Call<LoginResponse> login(
            @Body LoginRequest request
    );

    @GET("/api/users/email")
    Call<List<String>> getAllEmail();

    @GET("/api/users/{id}/friends")
    Call<List<User>> getFriends(
            @Path("id") int user_id
    );

    @GET("/api/users/{id}/profile")
    Call<LoginResponse> getProfile(
            @Path("id") int user_id
    );

    @PATCH("/api/users/{id}/profile")
    Call<UpdateProfileResponse> updateProfile(
            @Path("id") int user_id,
            @Body UpdateProfileRequest request
    );


    @POST("/api/posts")
    Call<CreatePostResponse> createPost(
            @Body CreatePostRequest request
    );

    @GET("/api/posts")
    Call<GetPostsResponse> getAllPosts();

    @GET("/api/posts/user/{id}")
    Call<GetPostsResponse> getPostsByUser(
            @Path("id") int user_id
    );

    @DELETE("/api/posts/{id}")
    Call<DeletePostResponse> deletePost(
            @Path("id") int post_id
    );
}
