package com.android.gitreposapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.android.gitreposapp.R
import com.android.gitreposapp.ui.RepositoryActivity
import com.android.gitreposapp.ui.RepositoryViewModel

class DetailRepositoryFragment : Fragment(R.layout.fragment_detail_repository) {

    lateinit var viewModel: RepositoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as RepositoryActivity).viewModel
    }
}