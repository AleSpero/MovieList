package com.sperolabs.movielist.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.sperolabs.movielist.R
import com.sperolabs.movielist.home.MainViewModel
import com.sperolabs.movielist.model.DataResult
import com.sperolabs.movielist.model.TvShow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.isSelectable
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        const val SHOW_ID_PARAM = "show_id"
    }

    val viewModel by viewModels<MainViewModel>()
    val inputSdf = SimpleDateFormat("yyyy-mm-dd", Locale.US)
    val outputSdf = SimpleDateFormat("dd MMM yyyy", Locale.US)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val showId = requireArguments().getInt(SHOW_ID_PARAM)
        collectUiUpdate(showId)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        })
    }

    fun collectUiUpdate(showId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getShowDetail(showId)
                .catch {
                    Log.e("DetailFragment", it.localizedMessage ?: "")
                }
                .collect { dataResult ->
                    withContext(Dispatchers.Main) {
                        when (dataResult) {
                            is DataResult.Success -> {
                                populateDetailView(dataResult.response.tvShow)
                                loading_overlay.visibility = View.GONE
                            }
                            is DataResult.Failure -> {
                                Log.e("DetailFragment", dataResult.error.localizedMessage ?: "")
                                populateErrorView(dataResult.error)
                            }
                        }
                    }
                }
        }
    }

    fun populateErrorView(throwable: Throwable){

    }

    fun populateDetailView(tvShow: TvShow) {
        show_title.text = tvShow.name

        if(tvShow.startDate?.isNotEmpty() == true) {
            try {
                first_aired.text = outputSdf.format(inputSdf.parse(tvShow.startDate) ?: Date())
            } catch(e : Exception){
                Log.e("Parsing Date", e.localizedMessage ?: "Error parsing date")
                first_aired.visibility = View.GONE
                first_aired_label.visibility = View.GONE
            }
        } else {
            first_aired.visibility = View.GONE
            first_aired_label.visibility = View.GONE
        }

        rating_text.text = String.format("%.1f", tvShow.rating / 2f)
        rating_bar.rating = (tvShow.rating / 2f).toFloat()
        ratings_count.text = String.format(getString(R.string.ratings_count), tvShow.ratingCount)

        Glide.with(this)
            .load(tvShow.fullImage)
            .into(show_image)

        show_description.text = HtmlCompat.fromHtml(tvShow.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        tvShow.genres.forEach {
            val chip = Chip(requireContext())
            chip.isSelectable = false
            chip.text = it
            chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_blue))
            chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))

            categories_container.addView(chip)
        }
    }

}