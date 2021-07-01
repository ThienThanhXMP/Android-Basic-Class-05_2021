package com.thanhthienxmp.githubsearch.data.model


import androidx.room.*

@Entity
data class GithubAccount(
    var avatar_url: String? = "", // https://avatars.githubusercontent.com/u/6941388?v=4
    var bio: String? = "", // - Senior Android (Kotlin and Java). - Cross platform: Flutter (Dart) and React Native (Javascript).
    var blog: String? = "",
    var company: String? = "", // Netacom
    var created_at: String? = "", // 2014-03-13T13:32:45Z
    var email: String? = "", // null
    var events_url: String? = "", // https://api.github.com/users/ToanMobile/events{/privacy}
    var followers: Int? = 0, // 39
    var followers_url: String? = "", // https://api.github.com/users/ToanMobile/followers
    var following: Int? = 0, // 34
    var following_url: String? = "", // https://api.github.com/users/ToanMobile/following{/other_user}
    var gists_url: String? = "", // https://api.github.com/users/ToanMobile/gists{/gist_id}
    var gravatar_id: String? = "",
    var hireable: String? = "", // null
    var html_url: String? = "", // https://github.com/ToanMobile
    var id: Int? = 0, // 6941388
    var location: String? = "", // HCM, Viet Nam.
    @PrimaryKey
    var login: String = "", // ToanMobile
    var name: String? = "", // Huỳnh Văn Toàn
    var node_id: String? = "", // MDQ6VXNlcjY5NDEzODg=
    var organizations_url: String? = "", // https://api.github.com/users/ToanMobile/orgs
    var public_gists: Int? = 0, // 5
    var public_repos: Int? = 0, // 53
    var received_events_url: String? = "", // https://api.github.com/users/ToanMobile/received_events
    var repos_url: String? = "", // https://api.github.com/users/ToanMobile/repos
    var site_admin: Boolean? = false, // false
    var starred_url: String? = "", // https://api.github.com/users/ToanMobile/starred{/owner}{/repo}
    var subscriptions_url: String? = "", // https://api.github.com/users/ToanMobile/subscriptions
    var twitter_username: String? = "", // null
    var type: String? = "", // User
    var updated_at: String? = "", // 2021-07-01T13:55:04Z
    var url: String? = "" // https://api.github.com/users/ToanMobile
)

@Entity
class GitFollowAccount(
    @PrimaryKey
    var git: String = "",
    var followLogin: String = "",
    var avatar: String? = "",
    var isFollower: Boolean = false
)
