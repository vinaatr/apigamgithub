package com.example.apigram2.data.model

data class ResponseDetailUser(
    val avatar_url: String,
    val bio: Any,
    val followers: Int,
    val following: Int,
    val id: Int,
    val login: String,
    val name: String,
)