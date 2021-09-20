package com.sperolabs.movielist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(
    val id : Int,
    val name : String,
    @SerializedName("start_date")
    val startDate : String?,
    @SerializedName("end_date")
    val endDate : String?,
    val country : String,
    val network : String,
    val status : String,
    val description : String,
    @SerializedName("image_thumbnail_path")
    val thumbnail : String,
    @SerializedName("image_path")
    val fullImage : String,
    val rating : Double,
    @SerializedName("rating_count")
    val ratingCount : Int,
    val genres : List<String>
) : Parcelable