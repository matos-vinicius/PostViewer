package br.edu.ifsp.scl.sc3039056.postviewer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.Comment
import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.LocalComment
import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.Post
import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.PostWithCommentCount
import br.edu.ifsp.scl.sc3039056.postviewer.model.network.PostsService
import br.edu.ifsp.scl.sc3039056.postviewer.model.repository.LocalCommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postsRepository = PostsService
    private val localCommentRepository = LocalCommentRepository

    // ─── Post selecionado (tela de detalhes) ───────────────────────────────
    private val _selectedPost = MutableStateFlow(Post())
    val selectedPost: StateFlow<Post> = _selectedPost.asStateFlow()
    fun setSelectedPost(post: Post) {
        _selectedPost.value = post
        updateComments()
        updateLocalComments()
    }

    // ─── Estados de carregamento e erro ────────────────────────────────────
    private val _isLoadingPosts = MutableStateFlow(false)
    val isLoadingPosts: StateFlow<Boolean> = _isLoadingPosts.asStateFlow()

    private val _postsErrorMessage = MutableStateFlow<String?>(null)
    val postsErrorMessage: StateFlow<String?> = _postsErrorMessage.asStateFlow()

    private val _isLoadingComments = MutableStateFlow(false)
    val isLoadingComments: StateFlow<Boolean> = _isLoadingComments.asStateFlow()

    private val _commentsErrorMessage = MutableStateFlow<String?>(null)
    val commentsErrorMessage: StateFlow<String?> = _commentsErrorMessage.asStateFlow()

    // ─── Lista de posts ─────────────────────────────────────────────────
    private val _posts = MutableStateFlow<List<PostWithCommentCount>>(emptyList())
    val posts: StateFlow<List<PostWithCommentCount>> = _posts.asStateFlow()

    fun updatePosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingPosts.value = true
            _postsErrorMessage.value = null
            try {
                val postsList = postsRepository.getPosts()
                val postsWithCounts = postsList.map { post ->
                    val comments = postsRepository.getCommentsByPostId(post.id!!)
                    PostWithCommentCount(
                        post = post,
                        commentCount = comments.size
                    )
                }

                _posts.value = postsWithCounts

            } catch (e: Exception) {
                _postsErrorMessage.value = "Não foi possível carregar os posts."
            }
            _isLoadingPosts.value = false
        }
    }

    // ─── Comentários da API (post selecionado) ─────────────────────────────
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    fun updateComments() {
        val postId = selectedPost.value.id ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingComments.value = true
            _commentsErrorMessage.value = null
            try {
                _comments.value = postsRepository.getCommentsByPostId(postId)
            } catch (e: Exception) {
                _commentsErrorMessage.value = "Não foi possível carregar os comentários."
            }
            _isLoadingComments.value = false
        }
    }

    // ─── Comentários locais (Room), associados ao post selecionado ─────────
    private val _localComments = MutableStateFlow<List<LocalComment>>(emptyList())
    val localComments: StateFlow<List<LocalComment>> = _localComments.asStateFlow()

    fun updateLocalComments() {
        val postId = selectedPost.value.id ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _localComments.value = localCommentRepository.getCommentsByPostId(postId)
        }
    }

    fun addLocalComment(authorName: String, body: String) {
        val postId = selectedPost.value.id ?: return
        if (body.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            localCommentRepository.addComment(
                LocalComment(
                    postId = postId,
                    authorName = authorName.ifBlank { "Você" },
                    body = body.trim()
                )
            )
            updateLocalComments()
        }
    }

    init {
        localCommentRepository.init(application)
        updatePosts()
    }
}