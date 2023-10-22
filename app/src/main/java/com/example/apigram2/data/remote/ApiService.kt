package com.example.apigram2.data.remote

import com.example.apigram2.BuildConfig
import com.example.apigram2.data.model.ResponseDetailUser
import com.example.apigram2.data.model.ResponseUser
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub(
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ResponseUser.Item>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): ResponseDetailUser

    @JvmSuppressWildcards
    @GET("/users/{username}/followers")
    suspend fun getFollowersUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ResponseUser.Item>

    @JvmSuppressWildcards
    @GET("/users/{username}/following")
    suspend fun getFollowingUserGithub(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<ResponseUser.Item>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun searchUserGithub(
        @QueryMap params: Map<String, Any>,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): ResponseUser
}