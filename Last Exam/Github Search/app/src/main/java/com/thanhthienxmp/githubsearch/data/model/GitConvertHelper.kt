package com.thanhthienxmp.githubsearch.data.model

class GitConvertHelper {
    fun crtToListFollowAccount(
        login: String,
        git: MutableList<GithubAccount>?
    ): MutableList<GitFollowAccount> {
        val list: MutableList<GitFollowAccount> = mutableListOf()
        git?.map {
            list.add(GitFollowAccount(it.login, login, it.avatar_url))
        }
        return list
    }
}