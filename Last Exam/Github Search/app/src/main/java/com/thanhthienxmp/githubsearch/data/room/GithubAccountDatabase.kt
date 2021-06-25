package com.thanhthienxmp.githubsearch.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thanhthienxmp.githubsearch.data.model.GitFollowAccount
import com.thanhthienxmp.githubsearch.data.model.GithubAccount

@Database(
    entities = [GithubAccount::class, GitFollowAccount::class],
    version = 1
)
abstract class GithubAccountDatabase : RoomDatabase() {
    abstract fun gitAccountDao(): GithubAccountDao

    companion object {
        private var instance: GithubAccountDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GithubAccountDatabase::class.java,
            "GitAccount"
        ).build()
    }
}