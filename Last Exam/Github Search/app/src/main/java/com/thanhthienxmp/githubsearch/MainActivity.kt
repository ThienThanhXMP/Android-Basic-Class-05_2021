package com.thanhthienxmp.githubsearch

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.thanhthienxmp.githubsearch.data.utils.GithubAccountAction
import com.thanhthienxmp.githubsearch.databinding.ActivityMainBinding
import com.thanhthienxmp.githubsearch.fragment.ProfileFragmentDirections


class MainActivity : AppCompatActivity(), GithubAccountAction {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    var isStacked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Define toolbar
        val toolbar = binding.toolbarContainer.toolbar
        setSupportActionBar(toolbar)
        setContentView(binding.root)

        // Init hide navigation icon
        getBackButton(init = false, stack = false)

        // Define navController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Github Search
        val gitEditText = binding.gitEditText
        gitEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                gitEditText.text?.let {
                    accessAccount(
                        it.toString().lowercase(),
                        init = true,
                        stack = false
                    )
                }
            false
        }
        binding.gitSearchBtn.setOnClickListener {
            gitEditText.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
            gitEditText.text?.let {
                accessAccount(
                    it.toString().lowercase(),
                    init = true,
                    stack = false
                )
            }
        }
    }

    // Click back button on device and back the last layer
    override fun onBackPressed() {
        super.onBackPressed()
        getBackButton(init = false, stack = false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                getBackButton(init = false, stack = false)
                navController.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()

    // This function to account sub github account
    override fun accessAccount(gitAccount: String?, init: Boolean, stack: Boolean) {
        Log.e("ACCESS", gitAccount.toString())
        getBackButton(init, stack)
        gitAccount?.let {
            navController.navigate(
                ProfileFragmentDirections.startProfile(
                    it, stack
                )
            )
        }
    }

    private fun getBackButton(init: Boolean, stack: Boolean) {
        isStacked = !init && stack
        supportActionBar?.setDisplayShowHomeEnabled(isStacked)
        supportActionBar?.setDisplayHomeAsUpEnabled(isStacked)
    }

    fun copyTextToClipboard(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("gitCopy", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this@MainActivity, "$text copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}