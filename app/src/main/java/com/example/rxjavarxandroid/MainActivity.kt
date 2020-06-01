package com.example.rxjavarxandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxjavarxandroid.adapters.RecyclerAdapter
import com.example.rxjavarxandroid.models.Comment
import com.example.rxjavarxandroid.models.Post
import com.example.rxjavarxandroid.service.ServiceGenerator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val TAG = "tagger"
    private val disposable = CompositeDisposable()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        getPostsObservable()
            .subscribeOn(Schedulers.io())
            .flatMap(Function<Post, ObservableSource<Post>>{
                getCommentsObservable(it)
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Post>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onNext(t: Post?) {
                    updatePost(t!!)
                }

                override fun onError(e: Throwable?) {
                    Log.e(TAG, "onError: $e")
                }
            })
    }

    private fun updatePost(post: Post){
        adapter.updatePost(post)
    }

    private fun getCommentsObservable(post: Post): Observable<Post> = ServiceGenerator.getRequestApi()
        .getComments(post.id)
        .map(Function<List<Comment>, Post>(){
            val delay = (Random().nextInt(3) + 1) * 1000
            Thread.sleep(delay.toLong())
            Log.d(TAG, "apply: sleeping thread ${Thread.currentThread().name} for $delay ms")
            post.comments = it
            post
        }).subscribeOn(Schedulers.io())


    private fun getPostsObservable() = ServiceGenerator.getRequestApi()
        .getPosts()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(Function<List<Post>, ObservableSource<Post>> {
            adapter.setPosts(ArrayList(it))
            Observable.fromIterable(it)
                .subscribeOn(Schedulers.io())

        })

    private fun initRecyclerView(){
        adapter = RecyclerAdapter()
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
