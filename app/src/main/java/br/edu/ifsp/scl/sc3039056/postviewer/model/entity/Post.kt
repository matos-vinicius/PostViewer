package br.edu.ifsp.scl.sc3039056.postviewer.model.entity

/**
 * Representa um post retornado pela API JSONPlaceholder.
 * GET https://jsonplaceholder.typicode.com/posts
 */
data class Post(
    var id: Int? = INVALID_POST_ID,
    var userId: Int = 0,
    var title: String = "",
    var body: String = ""
) {
    companion object {
        const val INVALID_POST_ID = -1
    }
}
