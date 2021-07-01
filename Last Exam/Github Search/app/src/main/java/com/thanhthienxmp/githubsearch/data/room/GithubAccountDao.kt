package com.thanhthienxmp.githubsearch.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thanhthienxmp.githubsearch.data.model.GitFollowAccount
import com.thanhthienxmp.githubsearch.data.model.GithubAccount

@Dao
abstract class GithubAccountDao {
    fun insertFollower(login: String, list: MutableList<GitFollowAccount>, isFollower: Boolean) {
        for (follower in list) {
            follower.apply {
                this.git = if (isFollower) follower.git else follower.git.plus("#F")
                this.followLogin = login
                this.isFollower = isFollower
            }
        }
        bInsertFollow(list)
    }

    fun insertGit(git: GithubAccount) = bInsertGit(git)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun bInsertGit(git: GithubAccount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun bInsertFollow(follower: MutableList<GitFollowAccount>)

    @Query("SELECT * FROM GithubAccount WHERE login = :gitLogin")
    abstract fun bGetAccount(gitLogin: String): GithubAccount

    @Query("SELECT * FROM GitFollowAccount WHERE followLogin = :gitLogin AND isFollower = 1")
    abstract fun bGetFollowerAccount(gitLogin: String): MutableList<GitFollowAccount>

    @Query("SELECT * FROM GitFollowAccount WHERE followLogin = :gitLogin AND isFollower = 0")
    abstract fun bGetFollowingAccount(gitLogin: String): MutableList<GitFollowAccount>

    @Query("DELETE FROM GithubAccount WHERE login = :gitLogin")
    abstract fun bDeleteAccount(gitLogin: String)
}