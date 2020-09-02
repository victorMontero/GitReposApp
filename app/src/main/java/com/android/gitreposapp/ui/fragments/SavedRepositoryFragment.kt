package com.android.gitreposapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.gitreposapp.R
import com.android.gitreposapp.adapters.RepositoryAdapter
import com.android.gitreposapp.ui.RepositoryActivity
import com.android.gitreposapp.ui.RepositoryViewModel
import kotlinx.android.synthetic.main.fragment_public_repository.*
import kotlinx.android.synthetic.main.fragment_saved_repository.*

class SavedRepositoryFragment : Fragment(R.layout.fragment_saved_repository) {

    lateinit var viewModel: RepositoryViewModel
    lateinit var repositoryAdapter: RepositoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as RepositoryActivity).viewModel
        setUpRecyclerView()

        repositoryAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("repositoryResponseItem", it)
            }
            findNavController().navigate(
                R.id.action_savedRepositoryFragment_to_detailRepositoryFragment,
                bundle
            )
        }
    }

    private fun setUpRecyclerView(){
        repositoryAdapter = RepositoryAdapter()
        recycler_view_saved_repository_fragment.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}