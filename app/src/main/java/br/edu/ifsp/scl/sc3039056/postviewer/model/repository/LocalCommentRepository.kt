package br.edu.ifsp.scl.sc3039056.postviewer.model.repository

import android.content.Context
import androidx.room.Room
import br.edu.ifsp.scl.sc3039056.postviewer.model.dao.LocalCommentDao
import br.edu.ifsp.scl.sc3039056.postviewer.model.database.LocalCommentDatabase
import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.LocalComment

object LocalCommentRepository {
    private lateinit var applicationContext: Context
    fun init(applicationContext: Context) {
        this.applicationContext = applicationContext
    }

    private val localCommentDaoImpl: LocalCommentDao by lazy {
        Room.databaseBuilder(
            applicationContext,
            LocalCommentDatabase::class.java,
            "local_comment_database"
        ).build().getLocalCommentDao()
    }

    fun getCommentsByPostId(postId: Int): List<LocalComment> =
        localCommentDaoImpl.getCommentsByPostId(postId)

    fun addComment(comment: LocalComment) = localCommentDaoImpl.addComment(comment)
}
