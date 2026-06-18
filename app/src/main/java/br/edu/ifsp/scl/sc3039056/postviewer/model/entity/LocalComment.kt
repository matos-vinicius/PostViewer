package br.edu.ifsp.scl.sc3039056.postviewer.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Comentário adicionado localmente pelo usuário, persistido via Room
 * e associado ao ID do post correspondente.
 */
@Entity(tableName = "local_comment")
data class LocalComment(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "post_id") var postId: Int = 0,
    @ColumnInfo(name = "author_name") var authorName: String = "",
    @ColumnInfo var body: String = ""
)
