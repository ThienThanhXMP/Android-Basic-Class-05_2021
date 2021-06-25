package com.thanhthienxmp.githubsearch.data.model


import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class GithubAccount(
    @SerializedName("avatar_url")
    var avatarUrl: String? = "", // https://avatars.githubusercontent.com/u/6941388?v=4
    @SerializedName("bio")
    var bio: String? = "", // - Senior Android (Kotlin and Java). - Cross platform: Flutter (Dart) and React Native (Javascript).
    @SerializedName("blog")
    var blog: String? = "",
    @SerializedName("company")
    var company: String? = "", // Netacom
    @SerializedName("created_at")
    var createdAt: String? = "", // 2014-03-13T13:32:45Z
    @SerializedName("email")
    var email: String? = "", // null
    @SerializedName("events_url")
    var eventsUrl: String? = "", // https://api.github.com/users/ToanMobile/events{/privacy}
    @SerializedName("followers")
    var followers: Int? = 0, // 40
    @SerializedName("followers_url")
    var followersUrl: String? = "", // https://api.github.com/users/ToanMobile/followers
    @SerializedName("following")
    var following: Int? = 0, // 34
    @SerializedName("following_url")
    var followingUrl: String? = "", // https://api.github.com/users/ToanMobile/following{/other_user}
    @SerializedName("gists_url")
    var gistsUrl: String? = "", // https://api.github.com/users/ToanMobile/gists{/gist_id}
    @SerializedName("gravatar_id")
    var gravatarId: String? = "",
    @SerializedName("hireable")
    var hireable: String? = "", // null
    @SerializedName("html_url")
    var htmlUrl: String? = "", // https://github.com/ToanMobile
    @SerializedName("id")
    var id: Int? = 0, // 6941388
    @SerializedName("location")
    var location: String? = "", // HCM, Viet Nam.
    @SerializedName("login")
    @PrimaryKey
    var login: String = "", // ToanMobile
    @SerializedName("name")
    var name: String? = "", // Huỳnh Văn Toàn
    @SerializedName("node_id")
    var nodeId: String? = "", // MDQ6VXNlcjY5NDEzODg=
    @SerializedName("organizations_url")
    var organizationsUrl: String? = "", // https://api.github.com/users/ToanMobile/orgs
    @SerializedName("public_gists")
    var publicGists: Int? = 0, // 5
    @SerializedName("public_repos")
    var publicRepos: Int? = 0, // 53
    @SerializedName("received_events_url")
    var receivedEventsUrl: String? = "", // https://api.github.com/users/ToanMobile/received_events
    @SerializedName("repos_url")
    var reposUrl: String? = "", // https://api.github.com/users/ToanMobile/repos
    @SerializedName("site_admin")
    var siteAdmin: Boolean? = false, // false
    @SerializedName("starred_url")
    var starredUrl: String? = "", // https://api.github.com/users/ToanMobile/starred{/owner}{/repo}
    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String? = "", // https://api.github.com/users/ToanMobile/subscriptions
    @SerializedName("twitter_username")
    var twitterUsername: String? = "", // null
    @SerializedName("type")
    var type: String? = "", // User
    @SerializedName("updated_at")
    var updatedAt: String? = "", // 2021-06-24T02:37:20Z
    @SerializedName("url")
    var url: String? = "", // https://api.github.com/users/ToanMobile
) {
    @Ignore
    val listFollower: GitFollow? = null

    @Ignore
    val listFollowing: GitFollow? = null
}

@Entity
class GitFollowAccount(
    @PrimaryKey
    var login: String = "",
    var followLogin: String? = "",
    var avatar: String? = ""
)

@Entity(primaryKeys = ["login", "followLogin"])
class UserWithFollowAccount(
    val login: String,
    val followLogin: String?,
)

