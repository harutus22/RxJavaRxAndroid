package com.example.rxjavarxandroid.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Comment(
    @Expose
    @SerializedName("postId")
    val postId: Int,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("body")
    val body: String
)

