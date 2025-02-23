package com.example.pagingv3.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.processing.Generated

data class TvShow (
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("permalink")
    @Expose
    var permalink: String? = null,

    @SerializedName("start_date")
    @Expose
    var startDate: String? = null,

    @SerializedName("end_date")
    @Expose
    var endDate: Any? = null,

    @SerializedName("country")
    @Expose
    var country: String? = null,

    @SerializedName("network")
    @Expose
    var network: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("image_thumbnail_path")
    @Expose
    var imageThumbnailPath: String? = null,

    var isFavourite: Boolean = false
)