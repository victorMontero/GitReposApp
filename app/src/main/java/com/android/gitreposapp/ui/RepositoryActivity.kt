package com.android.gitreposapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.gitreposapp.R
import com.android.gitreposapp.db.RepositoryDatabase
import com.android.gitreposapp.repository.RepoRepository
import kotlinx.android.synthetic.main.activity_repository.*

class RepositoryActivity : AppCompatActivity() {

    lateinit var viewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        val repoRepository = RepoRepository(RepositoryDatabase(this))
        val viewModelProviderFactory =
            RepositoryViewModelProviderFactory(application, repoRepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(RepositoryViewModel::class.java)

        bottom_navigation_view.setupWithNavController(repositoryNavHostFragment.findNavController())


    }
}