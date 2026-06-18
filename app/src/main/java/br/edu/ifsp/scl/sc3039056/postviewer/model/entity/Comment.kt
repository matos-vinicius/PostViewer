package br.edu.ifsp.scl.sc3039056.postviewer.model.entity

/**
 * Representa um comentário retornado pela API JSONPlaceholder.
 * GET https://jsonplaceholder.typicode.com/posts/{id}/comments
 */
data class Comment(
    var id: Int? = null,
    var postId: Int = 0,
    var name: String = "",
    var email: String = "",
    var body: String = ""
)
