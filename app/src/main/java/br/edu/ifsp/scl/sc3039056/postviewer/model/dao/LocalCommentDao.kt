package br.edu.ifsp.scl.sc3039056.postviewer.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.LocalComment

@Dao
interface LocalCommentDao {
    @Query("SELECT * FROM local_comment WHERE post_id = :postId")
    fun getCommentsByPostId(postId: Int): List<LocalComment>

    @Insert
    fun addComment(comment: LocalComment)
}
