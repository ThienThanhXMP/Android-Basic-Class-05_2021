package com.thanhthienxmp.githubsearch.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.thanhthienxmp.githubsearch.MainActivity
import com.thanhthienxmp.githubsearch.data.adapter.GithubFollowerAccountAdapter
import com.thanhthienxmp.githubsearch.data.adapter.GithubFollowingAccountAdapter
import com.thanhthienxmp.githubsearch.data.api.GithubApi
import com.thanhthienxmp.githubsearch.data.api.GithubApiService
import com.thanhthienxmp.githubsearch.data.model.*
import com.thanhthienxmp.githubsearch.data.room.GithubAccountDatabase
import com.thanhthienxmp.githubsearch.data.utils.ScreenUtils
import com.thanhthienxmp.githubsearch.data.utils.SingleSwipeItemAccessAccount
import com.thanhthienxmp.githubsearch.databinding.ProfileFragmentBinding
import com.thanhthienxmp.githubsearch.widget.RecyclerViewEmptySupport
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.util.*
import kotlin.math.pow
import kotlin.math.round

class ProfileFragment : Fragment() {
    // Initialize view binding
    private var bindingRoot: ProfileFragmentBinding? = null
    private val binding get() = bindingRoot!!

    private var gitAccount: String = "toanmobile"
    private val githubApi: GithubApi = GithubApiService.getService

    private lateinit var followerRcy: RecyclerViewEmptySupport
    private lateinit var followingRcy: RecyclerViewEmptySupport

    // Room Database
    private val db by lazy { GithubAccountDatabase((context as MainActivity).application).gitAccountDao() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingRoot = ProfileFragmentBinding.inflate(inflater, container, false)
        // Set height for divider
        binding.follow.divider.scaleY =
            ScreenUtils().getScreenHeight(activity as Activity).toFloat()

        // RecycleView
        followerRcy = binding.follow.followerRcy
        followerRcy.apply {
            layoutManager = LinearLayoutManager(this.context)
            setEmptyView(binding.follow.followerEmptyLayout)
            checkEmpty()
        }
        followingRcy = binding.follow.followingRcy
        followingRcy.apply {
            layoutManager = LinearLayoutManager(this.context)
            setEmptyView(binding.follow.followingEmptyLayout)
            checkEmpty()
        }

        // Check Fragment in Stack
        val isStacked = arguments?.getBoolean("checkStack") ?: false
        if (!isStacked) {
            // Swipe event to access account
            val followerItemTouchHelper = ItemTouchHelper(SingleSwipeItemAccessAccount)
            followerItemTouchHelper.attachToRecyclerView(followerRcy)

            val followingItemTouchHelper = ItemTouchHelper(SingleSwipeItemAccessAccount)
            followingItemTouchHelper.attachToRecyclerView(followingRcy)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get gitAccount
        gitAccount = this.arguments?.getString("gitAccount") ?: gitAccount
        // Use retrofit and coroutine to set UI
        displayAccount(gitAccount)
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingRoot = null
    }

    private fun displayAccount(login: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Display Info
            var git: GithubAccount? = db.bGetAccount(login)
            Log.e("TEST", git.toString())
            if (git == null) {
                val response = githubApi.getUser(login)
                try {
                    if (response.isSuccessful) {
                        git = response.body()
                    } else {
                        Log.e(
                            "Github Account Failed",
                            response.let { "${it.code()} - ${it.errorBody()}" })
                    }
                } catch (er: HttpException) {
                    Log.e("HttpException", er.toString())
                } catch (er: Throwable) {
                    Log.e("Error", er.toString())
                }
            }
            git?.let {
                withContext(Dispatchers.Main) {
                    setInfoUI(git)
                }
            }

            // Display Follow
            val followerResponse = githubApi.getUserFollowers(login, 100)
            val followingResponse = githubApi.getUserFollowing(login, 100)
            var listFollower = git?.listFollower
            var listFollowing = git?.listFollowing
            if (listFollower == null || listFollowing == null) {
                try {
                    if (followerResponse.isSuccessful) {
                        listFollower = GitConvertHelper().crtToListFollowAccount(
                            login,
                            followerResponse.body()
                        )
                        Log.e("LIST FOLLOWER", listFollower.toString())
                    } else {
                        Log.e(

                            "Github Followers Failed",
                            followerResponse.let { "${it.code()} - ${it.errorBody()}" })
                    }

                    if (followingResponse.isSuccessful) {
                        listFollowing = GitConvertHelper().crtToListFollowAccount(
                            login,
                            followingResponse.body()
                        )
                        Log.e("LIST FOLLOWING", listFollowing.toString())
                    } else {
                        Log.e(
                            "Github Following Failed",
                            followingResponse.let { "${it.code()} - ${it.errorBody()}" })
                    }

                } catch (er: HttpException) {
                    Log.e("HttpException", er.toString())
                } catch (er: Throwable) {
                    Log.e("Error", er.toString())
                }
            }

            withContext(Dispatchers.Main) {
                followerRcy.adapter =
                    listFollower?.let { GithubFollowerAccountAdapter(it, true) }
                followingRcy.adapter =
                    listFollowing?.let { GithubFollowingAccountAdapter(it, true) }
            }

            // Save git to database
            try {
                Log.e("TEST-GIT", git.toString())
                // Save follower to database
                if (git != null) {
                    listFollower?.let { db.insertFollower(git, it) }
                    // Save following to database
                    listFollowing?.let { db.insertFollowing(git, it) }
                    db.insertGitWithFollow(git)
                }
            } catch (e: Throwable) {
                Log.e("Insert Git", e.toString())
            }
        }
    }

    private fun setInfoUI(git: GithubAccount?) {
        val info = binding.info
        info.userName.text = git?.name
        info.userLogin.text = "@".plus(git?.login)
        info.userLogin.setOnClickListener {
            git?.login?.let { (context as MainActivity).copyTextToClipboard(it) }
        }
        info.userBio.text = git?.bio ?: "Bio Github Search"
        Glide.with(this@ProfileFragment).load(git?.avatarUrl)
            .into(info.userAvatar)
        info.userFollowers.text =
            getFollowNumber(git?.followers).plus(" Followers")
        info.userFollowing.text =
            getFollowNumber(git?.following).plus(" Following")
        info.userRepos.text = git?.publicRepos.toString().plus(" Repos")
    }

    private fun getFollowNumber(number: Int?): String {
        val mNumber = number ?: 0
        if (mNumber / 10.0.pow(6) > 1) {
            val million = round(mNumber / 10.0.pow(5)).toInt()
            return "${(million / 10.0).toInt()}M${million % 10}"
        }
        if (mNumber / 10.0.pow(3) > 1) {
            val thousand = round(mNumber / 10.0.pow(2)).toInt()
            return "${(thousand / 10.0).toInt()}K${thousand % 10}"
        }
        return number.toString()
    }
}

