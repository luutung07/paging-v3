package com.example.pagingv3.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TvShowResponse {
    @SerializedName("total")
    @Expose
    var total: Int? = null

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("pages")
    @Expose
    var pages: Int? = null

    @SerializedName("tv_shows")
    @Expose
    var tvShows: List<TvShow>? = null
}