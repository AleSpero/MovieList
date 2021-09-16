package com.sperolabs.movielist.model

import com.google.gson.annotations.SerializedName

class ShowsResponse(
    @SerializedName("page")
    val pageNumber : Int,
    @SerializedName("pages")
    val totalPages : Int,
    val results : List<TvShow>
)