package com.thanhthienxmp.githubsearch.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thanhthienxmp.githubsearch.data.model.GitFollow
import com.thanhthienxmp.githubsearch.data.model.GithubAccount

@Dao
abstract class GithubAccountDao {
    fun insertFollower(git: GithubAccount, list: GitFollow) {
        for (follower in list) {
            follower.login = git.login
        }
        bInsertFollower(list)
    }

    fun insertFollowing(git: GithubAccount, list: GitFollow) {
        for (following in list) {
            following.login = git.login
        }
        bInsertFollowing(list)
    }

    fun insertGitWithFollow(git: GithubAccount) {
        git.listFollower?.let { insertFollower(git, git.listFollower) }
        git.listFollowing?.let { insertFollowing(git, git.listFollowing) }
        bInsertGit(git)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun bInsertGit(git: GithubAccount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun bInsertFollower(follower: GitFollow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun bInsertFollowing(following: GitFollow)

    @Query("SELECT * FROM GithubAccount WHERE login = :gitLogin")
    abstract fun bGetAccount(gitLogin: String): GithubAccount

    @Query("DELETE FROM GithubAccount WHERE login = :gitLogin")
    abstract fun bDeleteAccount(gitLogin: String)
}