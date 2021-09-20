package com.sperolabs.movielist.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sperolabs.movielist.R
import com.sperolabs.movielist.detail.DetailFragment
import com.sperolabs.movielist.home.pagination.ShowsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    val adapter = ShowsAdapter { showId ->
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, Bundle().apply {
            putInt(DetailFragment.SHOW_ID_PARAM, showId)
        })
    }
    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        collectFlow()
    }

    fun setupRecyclerView(){
        showsList.layoutManager = LinearLayoutManager(requireContext())
        showsList.adapter = adapter
    }

    fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getShows().collect {
                adapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                showsList.isVisible = it.refresh is LoadState.NotLoading
                loading.isVisible = it.refresh is LoadState.Loading
            }
        }
    }

}