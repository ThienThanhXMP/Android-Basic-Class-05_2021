package com.thanhthienxmp.githubsearch.data.model

import com.google.gson.annotations.SerializedName

data class GithubFollowAccountItem(
    @SerializedName("avatar_url")
    val avatarUrl: String?, // https://avatars.githubusercontent.com/u/84616086?v=4
    @SerializedName("events_url")
    val eventsUrl: String?, // https://api.github.com/users/koolboylucky/events{/privacy}
    @SerializedName("followers_url")
    val followersUrl: String?, // https://api.github.com/users/koolboylucky/followers
    @SerializedName("following_url")
    val followingUrl: String?, // https://api.github.com/users/koolboylucky/following{/other_user}
    @SerializedName("gists_url")
    val gistsUrl: String?, // https://api.github.com/users/koolboylucky/gists{/gist_id}
    @SerializedName("gravatar_id")
    val gravatarId: String?,
    @SerializedName("html_url")
    val htmlUrl: String?, // https://github.com/koolboylucky
    @SerializedName("id")
    val id: Int?, // 84616086
    @SerializedName("login")
    val login: String?, // koolboylucky
    @SerializedName("node_id")
    val nodeId: String?, // MDQ6VXNlcjg0NjE2MDg2
    @SerializedName("organizations_url")
    val organizationsUrl: String?, // https://api.github.com/users/koolboylucky/orgs
    @SerializedName("received_events_url")
    val receivedEventsUrl: String?, // https://api.github.com/users/koolboylucky/received_events
    @SerializedName("repos_url")
    val reposUrl: String?, // https://api.github.com/users/koolboylucky/repos
    @SerializedName("site_admin")
    val siteAdmin: Boolean?, // false
    @SerializedName("starred_url")
    val starredUrl: String?, // https://api.github.com/users/koolboylucky/starred{/owner}{/repo}
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String?, // https://api.github.com/users/koolboylucky/subscriptions
    @SerializedName("type")
    val type: String?, // User
    @SerializedName("url")
    val url: String? // https://api.github.com/users/koolboylucky
)