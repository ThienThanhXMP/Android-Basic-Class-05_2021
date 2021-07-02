package com.thanhthienxmp.githubsearch

import android.content.*
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.thanhthienxmp.githubsearch.data.utils.GithubAccountAction
import com.thanhthienxmp.githubsearch.data.utils.NetworkConnection
import com.thanhthienxmp.githubsearch.databinding.ActivityMainBinding
import com.thanhthienxmp.githubsearch.fragment.ProfileFragmentDirections


class MainActivity : AppCompatActivity(), GithubAccountAction {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var networkConnection: NetworkConnection
    var isStacked = false

    @RequiresApi(Build.VERSION_CODES.N)
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

        // Reset text on click on
        gitEditText.setOnClickListener{
            gitEditText.text = null
        }

        // Event in search keyboard button
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

        // Event in search button
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

        // Broadcast Receiver
        networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, {
            runOnUiThread {
                if (it) {
                    binding.toolbarContainer.connectStateIcon.setImageResource(R.drawable.ic_online_state)
                    binding.toolbarContainer.connectStateText.text =
                        resources.getText(R.string.network_online_state)
                } else {
                    binding.toolbarContainer.connectStateIcon.setImageResource(R.drawable.ic_offline_state)
                    binding.toolbarContainer.connectStateText.text =
                        resources.getText(R.string.network_offline_state)
                    navController.navigate(
                        ProfileFragmentDirections.startAnnounce("no_internet")
                    )
                    getBackButton(init = false, stack = true)
                }
            }
        })
    }

    // Click back button on device and back the last layer
    override fun onBackPressed() {
        super.onBackPressed()
        getBackButton(init = false, stack = false)
    }

    // Back button in toolbar
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

    // Navigation initialize
    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()

    // This function to account sub github account
    override fun accessAccount(gitAccount: String?, init: Boolean, stack: Boolean) {
        getBackButton(init, stack)
        gitAccount?.let {
            navController.navigate(
                ProfileFragmentDirections.startProfile(
                    it.lowercase(), stack
                )
            )
        }
    }

    // Show back button
    private fun getBackButton(init: Boolean, stack: Boolean) {
        isStacked = !init && stack
        supportActionBar?.setDisplayShowHomeEnabled(isStacked)
        supportActionBar?.setDisplayHomeAsUpEnabled(isStacked)
    }

    // Copy in clipboard
    fun copyTextToClipboard(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("gitCopy", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this@MainActivity, "$text copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}