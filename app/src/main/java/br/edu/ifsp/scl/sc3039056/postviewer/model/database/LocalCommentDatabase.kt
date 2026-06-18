package br.edu.ifsp.scl.sc3039056.postviewer.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.sc3039056.postviewer.model.dao.LocalCommentDao
import br.edu.ifsp.scl.sc3039056.postviewer.model.entity.LocalComment

@Database(entities = [LocalComment::class], version = 1)
abstract class LocalCommentDatabase : RoomDatabase() {
    abstract fun getLocalCommentDao(): LocalCommentDao
}
