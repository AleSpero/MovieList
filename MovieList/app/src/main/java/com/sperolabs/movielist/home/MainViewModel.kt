package com.sperolabs.movielist.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sperolabs.movielist.model.DataResult
import com.sperolabs.movielist.model.SingleShowResponse
import com.sperolabs.movielist.model.TvShow
import com.sperolabs.movielist.network.ShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repo : ShowRepository) : ViewModel() {

    fun getShows() : Flow<PagingData<TvShow>> =
        repo.getPopularShows()
            .cachedIn(viewModelScope)

    fun getShowDetail(showId : Int) : Flow<DataResult<SingleShowResponse>> =
        flow { emit(repo.getShowDetail(showId)) }

}