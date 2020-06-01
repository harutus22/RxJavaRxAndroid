package com.example.rxjavarxandroid.service

import com.example.rxjavarxandroid.models.Comment
import com.example.rxjavarxandroid.models.Post
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {

    @GET("posts")
    fun getPosts(): Observable<List<Post>>

    @GET("post/{id}/comments")
    fun getComments(@Path("id") id: Int): Observable<List<Comment>>
}