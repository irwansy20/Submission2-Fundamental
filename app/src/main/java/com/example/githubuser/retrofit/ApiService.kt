package com.example.githubuser.retrofit

import com.example.githubuser.response.DetailResponse
import com.example.githubuser.FollowerResponse
import com.example.githubuser.FollowingResponse
import com.example.githubuser.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getSearch(
        @Query("q") q: String
    ): Call<SearchResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetail(
        @Path("username") name: String
    ): Call<DetailResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") name: String
    ): Call<List<FollowingResponse.FollowingResponseItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") name: String
    ): Call<List<FollowerResponse.FollowerResponseItem>>
}