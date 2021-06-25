package com.thanhthienxmp.githubsearch.data.model

class GitConvertHelper {
    fun crtToListFollowAccount(
        login: String,
        git: GithubFollowAccount?
    ): GitFollow {
        val list = GitFollow()
        git?.map {
            list.add(GitFollowAccount(login, it.login, it.avatarUrl))
        }
        return list
    }
}