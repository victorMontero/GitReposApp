package com.android.gitreposapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.gitreposapp.R
import com.android.gitreposapp.ui.RepositoryActivity
import com.android.gitreposapp.ui.RepositoryViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail_repository.*

class DetailRepositoryFragment : Fragment(R.layout.fragment_detail_repository) {

    lateinit var viewModel: RepositoryViewModel
    val args: DetailRepositoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as RepositoryActivity).viewModel

        val repository = args.repositoryResponseItem
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(repository.htmlUrl)
        }

        fab_to_favorite_repository.setOnClickListener {
            viewModel.saveRepository(repository)
            Snackbar.make(view, "repository has been saved", Snackbar.LENGTH_SHORT).show()
        }
    }
}