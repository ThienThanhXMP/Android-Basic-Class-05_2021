package com.thanhthienxmp.githubsearch.data.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thanhthienxmp.githubsearch.data.room.GithubAccountDao

class GitViewModelFactory(private val context: Application, private val db: GithubAccountDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T  = GitViewModel(context,db) as T
}