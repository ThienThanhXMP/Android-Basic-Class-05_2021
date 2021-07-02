package com.thanhthienxmp.githubsearch.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.thanhthienxmp.githubsearch.MainActivity
import com.thanhthienxmp.githubsearch.data.adapter.GithubFollowerAccountAdapter
import com.thanhthienxmp.githubsearch.data.adapter.GithubFollowingAccountAdapter
import com.thanhthienxmp.githubsearch.data.model.*
import com.thanhthienxmp.githubsearch.data.room.GithubAccountDatabase
import com.thanhthienxmp.githubsearch.data.utils.ScreenUtils
import com.thanhthienxmp.githubsearch.data.utils.SingleSwipeItemAccessAccount
import com.thanhthienxmp.githubsearch.data.viewmodel.GitViewModel
import com.thanhthienxmp.githubsearch.data.viewmodel.GitViewModelFactory
import com.thanhthienxmp.githubsearch.databinding.ProfileFragmentBinding
import com.thanhthienxmp.githubsearch.widget.RecyclerViewEmptySupport
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.pow
import kotlin.math.round

class ProfileFragment : Fragment() {
    // Initialize view binding
    private var bindingRoot: ProfileFragmentBinding? = null
    private val binding get() = bindingRoot!!

    private var gitAccount: String = "toanmobile"
    // private val githubApi: GithubApi = GithubApiService.getService

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
        val gitViewModel: GitViewModel by viewModels {
            GitViewModelFactory(
                (context as MainActivity).application,
                db
            )
        }
        // Get gitAccount
        gitAccount = this.arguments?.getString("gitAccount") ?: gitAccount
        // Use retrofit and coroutine to set UI
        gitViewModel.apply {
            getAccount(gitAccount) {
                if (it != null && it.login.isNotEmpty()) {
                    Log.e("TEST1", it.login)
                    setInfoUI(it)
                } else {
                    Log.e("TEST2", "TEST2")
                    view.findNavController().navigate(
                        ProfileFragmentDirections.startAnnounce("no_result")
                    )
                }
            }
            getGitFollowAccount(
                gitAccount
            ) { follow, following ->
                followerRcy.adapter =
                    GithubFollowerAccountAdapter(follow, true)
                followingRcy.adapter =
                    GithubFollowingAccountAdapter(following, true)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        bindingRoot = null
    }

    private fun setInfoUI(git: GithubAccount) {
        val info = binding.info
        info.userName.text = git.name
        info.userLogin.text = "@".plus(git.loginBias)
        info.userLogin.setOnClickListener {
            git.loginBias.let { (context as MainActivity).copyTextToClipboard(it) }
        }
        info.userBio.text = git.bio ?: "Bio Github Search"
        Glide.with(this@ProfileFragment).load(git.avatar_url)
            .into(info.userAvatar)
        info.userFollowers.text =
            getFollowNumber(git.followers).plus(" Followers")
        info.userFollowing.text =
            getFollowNumber(git.following).plus(" Following")
        info.userRepos.text = git.public_repos.toString().plus(" Repos")
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

