package com.android.gitreposapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.gitreposapp.R
import com.android.gitreposapp.adapters.RepositoryAdapter
import com.android.gitreposapp.ui.RepositoryActivity
import com.android.gitreposapp.ui.RepositoryViewModel
import com.google.android.material.snackbar.Snackbar
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

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val repository = repositoryAdapter.differ.currentList[position]
                viewModel.deleteRepository(repository)
                Snackbar.make(view, "repository has been deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("undo") {
                        viewModel.saveRepository(repository)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recycler_view_saved_repository_fragment)
        }

        viewModel.getSavedRepository().observe(viewLifecycleOwner, Observer { repositories ->
            repositoryAdapter.differ.submitList(repositories)
        })
    }

    private fun setUpRecyclerView() {
        repositoryAdapter = RepositoryAdapter()
        recycler_view_saved_repository_fragment.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}