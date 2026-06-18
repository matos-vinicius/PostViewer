package br.edu.ifsp.scl.sc3039056.postviewer.model.network

import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.Comment
import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.Post
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object PostsService {
    interface PostsApi {
        @GET("posts")
        fun getPosts(): Call<List<Post>>

        @GET("posts/{id}/comments")
        fun getCommentsByPostId(@Path("id") postId: Int): Call<List<Comment>>
    }

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val postsApi: PostsApi = retrofit.create(PostsApi::class.java)

    suspend fun getPosts(): List<Post> {
        val response = postsApi.getPosts().awaitResponse()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getCommentsByPostId(postId: Int): List<Comment> {
        val response = postsApi.getCommentsByPostId(postId).awaitResponse()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}
