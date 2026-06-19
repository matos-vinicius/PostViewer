package br.edu.ifsp.scl.sc3039056.postviewer.model.entity

/**
 * Representa um post retornado pela API JSONPlaceholder com a quantidade de comentários.
 * GET https://jsonplaceholder.typicode.com/posts
 * GET https://jsonplaceholder.typicode.com/posts/{id}/comments
 */
data class PostWithCommentCount(
    val post: Post,
    val commentCount: Int
)