package com.android.gitreposapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.gitreposapp.R
import com.android.gitreposapp.adapters.RepositoryAdapter
import com.android.gitreposapp.ui.RepositoryActivity
import com.android.gitreposapp.ui.RepositoryViewModel
import com.android.gitreposapp.util.Resource
import kotlinx.android.synthetic.main.fragment_public_repository.*

class PublicRepositoryFragment : Fragment(R.layout.fragment_public_repository) {

    lateinit var viewModel: RepositoryViewModel
    lateinit var repositoryAdapter: RepositoryAdapter

    val TAG = "PublicRepositoryFrag"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as RepositoryActivity).viewModel
        setUpRecyclerView()

        viewModel.repository.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let{repositoryResponse ->
                        repositoryAdapter.differ.submitList(repositoryResponse)

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progress_bar_public_repository_fragment.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progress_bar_public_repository_fragment.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView(){
        repositoryAdapter = RepositoryAdapter()
        recycler_view_public_repository_fragment.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}