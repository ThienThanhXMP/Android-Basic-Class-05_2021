package com.thanhthienxmp.githubsearch.data.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanhthienxmp.githubsearch.data.api.GithubApi
import com.thanhthienxmp.githubsearch.data.api.GithubApiService
import com.thanhthienxmp.githubsearch.data.model.GitConvertHelper
import com.thanhthienxmp.githubsearch.data.model.GitFollowAccount
import com.thanhthienxmp.githubsearch.data.model.GithubAccount
import com.thanhthienxmp.githubsearch.data.room.GithubAccountDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class GitViewModel(context: Context, private val db: GithubAccountDao) : ViewModel() {
    private val githubApi: GithubApi = GithubApiService.getService

    fun getAccount(login: String, returnGit: (GithubAccount?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            // Display Info
            var isUpdate = false
            var git: GithubAccount? = db.bGetAccount(login)
            if (git == null) {
                var response: Response<GithubAccount>? = null
                try {
                    response = githubApi.getUser(login)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (response != null) {
                    if (response.isSuccessful) {
                        git = response.body()?.apply {
                            this.loginBias = this.login
                            this.login = this.login.lowercase()
                        }
                        isUpdate = true
                    } else {
                        Log.e(
                            "Github Account Failed",
                            response.let { "${it.code()} - ${it.errorBody()}" })
                    }
                }
            }

            if (isUpdate) git?.let { db.insertGit(it) }
            withContext(Dispatchers.Main) {
                returnGit(git)
            }

        }
    }

    fun getGitFollowAccount(
        login: String,
        returnFollow: (MutableList<GitFollowAccount>, MutableList<GitFollowAccount>) -> Unit
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            // Display Follow
            var isFollowUpdate = false
            var listFollower = db.bGetFollowerAccount(login)
            var listFollowing = db.bGetFollowingAccount(login)
            listFollowing.map {
                it.git = it.git.split("#F").first()
            }
            if (listFollower.isEmpty() && listFollowing.isEmpty()) {
                var followerResponse: Response<MutableList<GithubAccount>>? = null
                var followingResponse: Response<MutableList<GithubAccount>>? = null
                try {
                    followerResponse = githubApi.getUserFollowers(login, 100)
                    followingResponse = githubApi.getUserFollowing(login, 100)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                isFollowUpdate = true
                if (followerResponse != null) {
                    if (followerResponse.isSuccessful) {
                        listFollower = GitConvertHelper().crtToListFollowAccount(
                            login,
                            followerResponse.body()
                        )
                    } else {
                        Log.e(
                            "Github Followers Failed",
                            followerResponse.let { "${it.code()} - ${it.errorBody()}" })
                    }
                }

                if (followingResponse != null) {
                    if (followingResponse.isSuccessful) {
                        listFollowing = GitConvertHelper().crtToListFollowAccount(
                            login,
                            followingResponse.body()
                        )
                    } else {
                        Log.e(
                            "Github Following Failed",
                            followingResponse.let { "${it.code()} - ${it.errorBody()}" })
                    }
                }
            }

            // Save git to database
            if (isFollowUpdate) {
                db.insertFollower(login, listFollower, true)
                // Save following to database
                db.insertFollower(login, listFollowing, false)
            }

            withContext(Dispatchers.Main) {
                returnFollow(listFollower, listFollowing)
            }
        }
    }
}