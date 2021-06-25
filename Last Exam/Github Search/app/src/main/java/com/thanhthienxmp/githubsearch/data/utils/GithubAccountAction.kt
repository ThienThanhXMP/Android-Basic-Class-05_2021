package com.thanhthienxmp.githubsearch.data.utils

interface GithubAccountAction {
    fun accessAccount(gitAccount: String?, init: Boolean, stack: Boolean)
}